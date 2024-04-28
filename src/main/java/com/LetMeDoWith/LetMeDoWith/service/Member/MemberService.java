package com.LetMeDoWith.LetMeDoWith.service.Member;


import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberSocialAccount;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberType;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberRepository;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberSocialAccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final MemberSocialAccountRepository memberSocialAccountRepository;
    
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
}