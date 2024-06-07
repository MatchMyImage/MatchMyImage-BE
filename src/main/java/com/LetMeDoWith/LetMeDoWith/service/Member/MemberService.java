package com.LetMeDoWith.LetMeDoWith.service.Member;


import com.LetMeDoWith.LetMeDoWith.dto.requestDto.CreateMemberTermAgreeReq;
import com.LetMeDoWith.LetMeDoWith.dto.requestDto.SignupCompleteReq;
import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberSocialAccount;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberTermAgree;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberType;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.provider.AuthTokenProvider;
import com.LetMeDoWith.LetMeDoWith.provider.AuthTokenProvider.TokenType;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberRepository;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberSocialAccountRepository;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberTermAgreeRepository;
import com.LetMeDoWith.LetMeDoWith.util.AuthUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final MemberSocialAccountRepository memberSocialAccountRepository;
    private final MemberTermAgreeRepository memberTermAgreeRepository;
    private final AuthTokenProvider authTokenProvider;
    
    /**
     * (Provider, Email) 의 조합으로 기 가입된 계정이 존재하는지 확인한다.
     *
     * @param provider
     * @param email
     * @return 기 가입된 계정. Optional 타입을 리턴한다..
     */
    public Optional<Member> getRegisteredMember(SocialProvider provider, String email) {
        return memberRepository.findByProviderAndEmail(provider, email);
    }
    
    /**
     * 소셜 인증 완료 후 임시 멤버를 생성한다.
     *
     * @param provider 소셜 인증 제공자
     * @param email    유저 이메일
     * @return 임시 멤버 객체
     */
    @Transactional
    public Member createSocialAuthenticatedMember(SocialProvider provider, String email) {
        Member temporalMember = memberRepository.save(Member.builder()
                                                            .email(email)
                                                            .type(MemberType.USER)
                                                            .status(MemberStatus.SOCIAL_AUTHENTICATED)
                                                            .build());
        
        MemberSocialAccount socialAccount = MemberSocialAccount.builder()
                                                               .member(temporalMember)
                                                               .provider(provider)
                                                               .build();
        // 양방향 연관관계 매핑
        temporalMember.getSocialAccountList().add(socialAccount);
        memberSocialAccountRepository.save(socialAccount);
        
        return temporalMember;
    }
    
    /**
     * 회원가입 완료 요청을 처리하여 Member 정보를 업데이트한다.
     *
     * @param signupCompleteReq 회원가입을 완료하려는 회원의 나머지 추가 정보
     * @return 업데이트된 멤버 객체
     * @throws RestApiException 유효하지 않은 토큰이거나, memberId가 유효하지 않은 경우
     */
    @Transactional
    public Member createSignupCompletedMember(SignupCompleteReq signupCompleteReq) {
        Long memberId = authTokenProvider.validateToken(signupCompleteReq.signupToken(),
                                                        TokenType.SIGNUP);
        
        return memberRepository.findById(memberId).map(member -> {
            member.setNickname(signupCompleteReq.nickname());
            member.setDateOfBirth(signupCompleteReq.dateOfBirth());
            member.setGender(signupCompleteReq.gender());
            
            // 수정된 멤버를 저장하고 반환
            return memberRepository.save(member);
        }).orElseThrow(() -> new RestApiException(FailResponseStatus.INVALID_TOKEN));
    }
    
    /**
     * 회원의 약관 동의 정보를 생성한다.
     *
     * @param createMemberTermAgreeReq 회원의 약관 동의 정보를 담은 요청 객체
     * @throws RestApiException 필수 동의 항목이 false이거나, 회원이 존재하지 않을 경우
     */
    public void createMemberTermAgree(CreateMemberTermAgreeReq createMemberTermAgreeReq) {
        Long memberId = AuthUtil.getMemberId();
        
        Boolean termsOfAgree = createMemberTermAgreeReq.termsOfAgree();
        Boolean privacy = createMemberTermAgreeReq.privacy();
        Boolean advertisement = createMemberTermAgreeReq.advertisement();
        
        // 필수 동의항목 (이용약관동의, 개인정보)가 false인 경우 exception
        if (!termsOfAgree || !privacy) {
            throw new RestApiException(FailResponseStatus.INVALID_PARAM_ERROR);
        }
        
        memberRepository
            .findById(memberId)
            .ifPresentOrElse(member -> {
                                 MemberTermAgree memberTermAgree = MemberTermAgree.builder()
                                                                                  .termsOfAgree(termsOfAgree)
                                                                                  .privacy(privacy)
                                                                                  .advertisement(advertisement)
                                                                                  .member(member)
                                                                                  .build();
                
                                 member.setTermAgree(memberTermAgree);
                
                                 memberTermAgreeRepository.save(memberTermAgree);
                                 memberRepository.save(member);
                             }
                , () -> {
                    // memberId가 존재하지 않아도 exception
                    throw new RestApiException(FailResponseStatus.MEMBER_NOT_EXIST);
                }
            );
    }
}