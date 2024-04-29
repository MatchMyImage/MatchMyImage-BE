package com.LetMeDoWith.LetMeDoWith.repository.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.LetMeDoWith.LetMeDoWith.config.JpaAuditingConfiguration;
import com.LetMeDoWith.LetMeDoWith.config.TestQueryDslConfig;
import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberSocialAccount;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberType;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({JpaAuditingConfiguration.class, TestQueryDslConfig.class})
class MemberSocialAccountRepositoryTest {
    
    @Autowired
    TestEntityManager entityManager;
    
    @Autowired
    MemberRepository memberRepository;
    
    @Autowired
    MemberSocialAccountRepository memberSocialAccountRepository;
    
    @BeforeEach
    void beforeEach() {
        entityManager.clear();
        
        Member testMemberObj = Member.builder()
                                     .email("test@email.com")
                                     .nickname("nickname")
                                     .selfDescription("self desc")
                                     .status(MemberStatus.NORMAL)
                                     .type(MemberType.USER)
                                     .profileImageUrl("image.jpeg")
                                     .build();
        
        memberRepository.save(testMemberObj);
    }
    
    @Test
    @DisplayName("[SUCCESS] MemberSocialAccount 생성 및 조회")
    void InsertNewMemberSocialAccountEntityTest() {
        Optional<Member> memberOptional = memberRepository.findByEmail("test@email.com");
        Member member = memberOptional.get();
        
        MemberSocialAccount memberSocialAccountKaKao = MemberSocialAccount.builder()
                                                                          .member(member)
                                                                          .provider(SocialProvider.KAKAO)
                                                                          .build();
        
        MemberSocialAccount memberSocialAccountGoogle = MemberSocialAccount.builder()
                                                                           .member(member)
                                                                           .provider(SocialProvider.GOOGLE)
                                                                           .build();
        
        memberSocialAccountRepository.save(memberSocialAccountKaKao);
        memberSocialAccountRepository.save(memberSocialAccountGoogle);
        
        Optional<MemberSocialAccount> kakaoAccount = memberSocialAccountRepository.findByMemberAndProvider(
            member,
            SocialProvider.KAKAO);
        
        Optional<MemberSocialAccount> googleAccount = memberSocialAccountRepository.findByMemberAndProvider(
            member,
            SocialProvider.GOOGLE);
        
        assertEquals(kakaoAccount.get().getMember().getNickname(), "nickname");
        assertEquals(googleAccount.get().getMember().getNickname(), "nickname");
        assertEquals(kakaoAccount.get().getMember(), googleAccount.get().getMember());
    }
    
}