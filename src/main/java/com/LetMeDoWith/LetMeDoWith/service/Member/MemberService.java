package com.LetMeDoWith.LetMeDoWith.service.Member;


import com.LetMeDoWith.LetMeDoWith.dto.command.CreateSignupCompletedMemberCommand;
import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberSocialAccount;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberTermAgree;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberType;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.provider.AuthTokenProvider;
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
     * (Provider, Subject) 의 조합으로 기 가입된 계정이 존재하는지 확인한다.
     *
     * @param provider
     * @param subject
     * @return 기 가입된 계정. Optional 타입을 리턴한다..
     */
    public Optional<Member> getRegisteredMember(SocialProvider provider, String subject) {
        return memberRepository.findByProviderAndSubject(provider, subject);
    }
    
    /**
     * 소셜 인증 완료 후 임시 멤버를 생성한다.
     *
     * @param provider 소셜 인증 제공자
     * @param subject  provider별 유저 고유번호
     * @return 임시 멤버 객체
     */
    @Transactional
    public Member createSocialAuthenticatedMember(SocialProvider provider, String subject) {
        Member temporalMember = memberRepository.save(Member.builder()
                                                            .subject(subject)
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
     * @param command 회원가입 완료 요청
     * @return 업데이트된 멤버 객체
     * @throws RestApiException 유효하지 않은 토큰이거나, memberId가 유효하지 않은 경우
     */
    @Transactional
    public Member createSignupCompletedMember(CreateSignupCompletedMemberCommand command) {
        Long memberId = AuthUtil.getMemberId();
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        
        if (optionalMember.isPresent()) {
            if (isExistingNickname(command.nickname())) {
                throw new RestApiException(FailResponseStatus.DUPLICATE_NICKNAME);
            }
            
            Member member = optionalMember.get();
            
            member.setNickname(command.nickname());
            member.setDateOfBirth(command.dateOfBirth());
            member.setGender(command.gender());
            member.setStatus(MemberStatus.NORMAL);
            
            createMemberTermAgree(command.isTerms(),
                                  command.isPrivacy(),
                                  command.isAdvertisement());
            
            return memberRepository.save(member);
        } else {
            throw new RestApiException(FailResponseStatus.INVALID_TOKEN);
        }
    }
    
    /**
     * 회원의 약관 동의 정보를 생성한다.
     *
     * @param isTerms
     * @param isPrivacy
     * @param isAdvertisement
     * @throws RestApiException 필수 동의 항목이 false이거나, 회원이 존재하지 않을 경우
     */
    @Transactional
    public void createMemberTermAgree(boolean isTerms, boolean isPrivacy, boolean isAdvertisement) {
        Long memberId = AuthUtil.getMemberId();
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        
        if (optionalMember.isPresent()) {
            if (!isTerms || !isPrivacy) {
                throw new RestApiException(FailResponseStatus.INVALID_PARAM_ERROR);
            }
            
            Member member = optionalMember.get();
            
            MemberTermAgree memberTermAgree = memberTermAgreeRepository.save(
                MemberTermAgree.builder()
                               .termsOfAgree(isTerms)
                               .privacy(isPrivacy)
                               .advertisement(isAdvertisement)
                               .member(member)
                               .build()
            );
            
            member.setTermAgree(memberTermAgree);
            memberRepository.save(member);
        } else {
            throw new RestApiException(FailResponseStatus.MEMBER_NOT_EXIST);
        }
    }
    
    /**
     * 닉네임의 중복 여부를 확인한다.
     *
     * @param nickname 중복여부를 확인하려는 닉네임
     * @return 닉네임의 중복 여부
     */
    public boolean isExistingNickname(String nickname) {
        if (nickname.trim().isEmpty()) {
            throw new RestApiException(FailResponseStatus.MANDATORY_PARAM_ERROR_NAME);
        }
        
        return memberRepository.findByNickname(nickname.trim()).isPresent();
    }
}