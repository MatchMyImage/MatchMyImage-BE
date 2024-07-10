package com.LetMeDoWith.LetMeDoWith.service;

import com.LetMeDoWith.LetMeDoWith.dto.responseDto.CreateTokenRefreshResDto;
import com.LetMeDoWith.LetMeDoWith.dto.responseDto.CreateTokenResDto;
import com.LetMeDoWith.LetMeDoWith.dto.valueObject.AuthTokenVO;
import com.LetMeDoWith.LetMeDoWith.entity.auth.RefreshToken;
import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.provider.AuthTokenProvider;
import com.LetMeDoWith.LetMeDoWith.provider.OidcIdTokenProvider;
import com.LetMeDoWith.LetMeDoWith.repository.auth.RefreshTokenRedisRepository;
import com.LetMeDoWith.LetMeDoWith.service.Member.MemberService;
import com.LetMeDoWith.LetMeDoWith.util.HeaderUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final AuthTokenProvider authTokenProvider;
    
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    
    private final SocialProviderAuthFactoryService socialProviderAuthFactoryService;
    
    private final MemberService memberService;
    
    private final OidcIdTokenProvider oidcIdTokenProvider;
    
    public CreateTokenRefreshResDto createTokenRefresh(String accessToken,
                                                       String refreshToken,
                                                       String userAgent) {
        
        Long memberId = authTokenProvider.validateToken(refreshToken,
                                                        AuthTokenProvider.TokenType.RTK);
        
        // Redis에서 RTK info 조회
        RefreshToken savedRefreshToken = refreshTokenRedisRepository.findById(refreshToken)
                                                                    .orElseThrow(() -> new RestApiException(
                                                                        FailResponseStatus.TOKEN_EXPIRED_BY_ADMIN));
        
        // RTK 추가 보안 점검
        if (!savedRefreshToken.getMemberId().equals(memberId)) {
            throw new RestApiException(FailResponseStatus.INVALID_RTK_TOKEN_MEMBER_NOT_MATCHED);
        } else if (!savedRefreshToken.getAccessToken().equals(accessToken)) {
            throw new RestApiException(FailResponseStatus.INVALID_RTK_TOKEN_ATK_NOT_MATCHED);
        }
        
        // 신규 ATK 발급
        AuthTokenVO authTokenVO = authTokenProvider.createAccessToken(memberId);
        
        // 신규 RTK 발급
        RefreshToken newRefreshToken = authTokenProvider.createRefreshToken(memberId,
                                                                            authTokenVO.token(),
                                                                            userAgent);
        
        // 기존 RTK info Redis에서 삭제
        refreshTokenRedisRepository.delete(savedRefreshToken);
        
        return CreateTokenRefreshResDto.builder()
                                       .accessToken(authTokenVO.token())
                                       .accessTokenExpireAt(authTokenVO.expireAt())
                                       .refreshToken(newRefreshToken.getToken())
                                       .build();
        
    }
    
    /**
     * 회원가입 여부를 판단하여 ATK를 발급한다.
     * <p>
     * 입력으로 받은 DTO의 정보를 통해 기 가입 여부를 판단하여, 이미 가입된 유저가 발급을 요청하는 경우 ATK를 발급하고, 아닌 경우 회원가입 프로세스를 진행한다.
     *
     * @param socialProvider
     * @param idToken
     * @return 기 가입되어 있는 경우 ATK, 아닌 경우 회원가입 프로세스로 fallback.
     */
    public CreateTokenResDto createToken(SocialProvider socialProvider, String idToken) {
        Jws<Claims> verifiedIdToken = oidcIdTokenProvider.getVerifiedOidcIdToken(socialProvider,
                                                                                 idToken);
        
        Claims body = verifiedIdToken.getBody();
        String sub = body.get("sub", String.class);
        
        Optional<Member> optionalMember = memberService.getRegisteredMember(socialProvider, sub);
        
        // 기 가입된 유저가 있으면, 로그인(액세스 토큰을 발급)한다.
        // 가입된 유저가 없으면, 회원가입 프로세스를 진행한다.
        return optionalMember.map(this::getToken)
                             .orElseGet(() -> createTemporalMember(socialProvider, sub));
    }
    
    
    /**
     * access token과 refresh token을 발급, 즉 로그인한다.
     *
     * @param member 토큰을 발급하려는 (로그인하려는) 멤버
     * @return access token 및 refresh token.
     */
    public CreateTokenResDto getToken(Member member) {
        
        if (member.getStatus().equals(MemberStatus.NORMAL)) {
            AuthTokenVO accessToken = authTokenProvider.createAccessToken(member.getId());
            RefreshToken refreshToken = authTokenProvider.createRefreshToken(member.getId(),
                                                                             accessToken.token(),
                                                                             HeaderUtil.getUserAgent());
            
            return CreateTokenResDto.builder()
                                    .atk(accessToken)
                                    .rtk(refreshToken)
                                    .build();
        } else {
            throw new RestApiException(member.getStatus().getApiResponseStatus());
        }
        
    }
    
    /**
     * 최초 소셜 로그인 시도시 회원가입 단계를 진행하기 위해 임시 Member를 생성한다. 생성된 임시 Member는 필요한 정보가 들어있지 않은 상태이다.
     * <p>
     * 이후 회원가입이 완료될 때 Client에서 넘어오는 정보를 가지고 임시 Member를 업데이트한다.
     */
    private CreateTokenResDto createTemporalMember(SocialProvider provider, String subject) {
        log.info("Not registered user!");
        Member member = memberService.createSocialAuthenticatedMember(provider,
                                                                      subject);
        
        return CreateTokenResDto.builder()
                                .signupToken(authTokenProvider.createSignupToken(member.getId()))
                                .build();
    }
    
    
}