package com.LetMeDoWith.LetMeDoWith.service;

import com.LetMeDoWith.LetMeDoWith.client.AuthClient;
import com.LetMeDoWith.LetMeDoWith.dto.requestDto.CreateAccessTokenReqDto;
import com.LetMeDoWith.LetMeDoWith.dto.responseDto.CreateTokenRefreshResDto;
import com.LetMeDoWith.LetMeDoWith.dto.responseDto.client.OidcPublicKeyResDto;
import com.LetMeDoWith.LetMeDoWith.dto.responseDto.client.OidcPublicKeyResDto.OidcPublicKeyVO;
import com.LetMeDoWith.LetMeDoWith.dto.valueObject.AccessTokenVO;
import com.LetMeDoWith.LetMeDoWith.entity.auth.RefreshToken;
import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.exception.OidcIdTokenPublicKeyNotFoundException;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.provider.AuthTokenProvider;
import com.LetMeDoWith.LetMeDoWith.repository.auth.RefreshTokenRedisRepository;
import com.LetMeDoWith.LetMeDoWith.service.Member.MemberService;
import com.LetMeDoWith.LetMeDoWith.util.AuthUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final AuthTokenProvider authTokenProvider;
    
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    
    private final SocialProviderAuthFactoryService socialProviderAuthFactoryService;
    
    private final MemberService memberService;
    
    @Value("${auth.oidc.aud.kakao}")
    private String KAKAO_AUD;
    
    @Value("${auth.oidc.aud.google}")
    private String GOOGLE_AUD;
    
    @Value("${auth.oidc.aud.apple}")
    private String APPLE_AUD;
    
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
        AccessTokenVO accessTokenVO = authTokenProvider.createAccessToken(memberId);
        
        // 신규 RTK 발급
        RefreshToken newRefreshToken = authTokenProvider.createRefreshToken(memberId,
                                                                            accessTokenVO.token(),
                                                                            userAgent);
        
        // 기존 RTK info Redis에서 삭제
        refreshTokenRedisRepository.delete(savedRefreshToken);
        
        return CreateTokenRefreshResDto.builder()
                                       .accessToken(accessTokenVO.token())
                                       .accessTokenExpireAt(accessTokenVO.expireAt())
                                       .refreshToken(newRefreshToken.getToken())
                                       .build();
        
    }
    
    /**
     * 회원가입 여부를 판단하여 ATK를 발급한다.
     * <p>
     * 입력으로 받은 DTO의 정보를 통해 기 가입 여부를 판단하여, 이미 가입된 유저가 발급을 요청하는 경우 ATK를 발급하고, 아닌 경우 회원가입 프로세스를 진행한다.
     *
     * @param createAccessTokenReqDto 발급 요청자의 Provider, 이메일 정보
     * @return 기 가입되어 있는 경우 ATK, 아닌 경우 회원가입 프로세스로 fallback.
     */
    public Optional<AccessTokenVO> createToken(CreateAccessTokenReqDto createAccessTokenReqDto) {
        Jws<Claims> verifiedIdToken = getVerifiedOidcIdToken(createAccessTokenReqDto.provider(),
                                                             createAccessTokenReqDto.idToken());
        
        Claims body = verifiedIdToken.getBody();
        String email = body.get("email", String.class);
        
        Optional<Member> optionalMember = memberService.getRegisteredMember(
            createAccessTokenReqDto.provider(),
            email);
        
        if (optionalMember.isPresent()) {
            // 기 가입된 유저가 있으면, 로그인(액세스 토큰을 발급)한다.
            return Optional.of(login(optionalMember.get()));
        } else {
            // 가입된 유저가 없으면, 회원가입 프로세스를 진행한다.
            proceedToSignup(createAccessTokenReqDto);
            
            return Optional.empty();
        }
    }
    
    /**
     * Signature 검증이 완료돤 ID Token을 얻는다.
     *
     * @param token    검증하려는 인코딩된 ID token
     * @param provider 자격증명 제공자
     * @return 서명을 검증 완료한 ID Token.
     */
    private Jws<Claims> getVerifiedOidcIdToken(SocialProvider provider, String token) {
        AuthClient client = socialProviderAuthFactoryService.getClient(provider);
        OidcPublicKeyResDto publicKeyList = client.getPublicKeyList().block();
        String aud = getAudValueForProvider(provider);
        String kid = AuthUtil.getKidFromUnsignedTokenHeader(token, aud, provider.getIssUrl());
        
        try {
            
            OidcPublicKeyVO keyVO = publicKeyList.keys().stream()
                                                 .filter(key -> key.kid().equals(kid))
                                                 .findFirst()
                                                 .orElseThrow(OidcIdTokenPublicKeyNotFoundException::new);
            
            return AuthUtil.verifyOidcToken(token, keyVO.n(), keyVO.e());
            
        } catch (OidcIdTokenPublicKeyNotFoundException e) {
            log.error("일치하는 OIDC ID Token 공개키 API가 없습니다. Cache를 갱신합니다.");
            // TODO: add method invalidates cache for public key.
            // invalidateCache()
            
            // Cache를 무효화 한 후, 공개키를 다시 조회한다.
            try {
                publicKeyList = client.getPublicKeyList().block();
                
                OidcPublicKeyVO keyVO = publicKeyList.keys().stream()
                                                     .filter(key -> key.kid().equals(kid))
                                                     .findFirst()
                                                     .orElseThrow(
                                                         OidcIdTokenPublicKeyNotFoundException::new);
                
                return AuthUtil.verifyOidcToken(token, keyVO.n(), keyVO.e());
            } catch (OidcIdTokenPublicKeyNotFoundException exception) {
                log.error("OIDC ID Token 공개키 갱신 실패. {} 공개키 서버의 문제일 수 있습니다.",
                          provider.getDescription());
                throw new RestApiException(FailResponseStatus.OIDC_ID_TOKEN_PUBKEY_NOT_FOUND);
            }
        }
    }
    
    
    /**
     * 로그인, 즉 access token을 발급한다.
     *
     * @param member
     * @return access token
     */
    private AccessTokenVO login(Member member) {
        return authTokenProvider.createAccessToken(member.getId());
    }
    
    /**
     * 최초 소셜 로그인 시도시 회원가입 단계를 진행하기 위해 임시 Member를 생성한다. 생성된 임시 Member는 필요한 정보가 들어있지 않은 상태이다.
     * <p>
     * 이후 회원가입이 완료될 때 Client에서 넘어오는 정보를 가지고 임시 Member를 업데이트한다.
     *
     * @param createAccessTokenReqDto
     */
    private void proceedToSignup(CreateAccessTokenReqDto createAccessTokenReqDto) {
        // some processes.
        log.info("Not registered user!");
    }
    
    private String getAudValueForProvider(SocialProvider provider) {
        switch (provider) {
            case APPLE -> {
                return APPLE_AUD;
            }
            
            case GOOGLE -> {
                return GOOGLE_AUD;
            }
            
            case KAKAO -> {
                return KAKAO_AUD;
            }
            
            default -> throw new RuntimeException();
        }
    }
    
}