package com.LetMeDoWith.LetMeDoWith.repository.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.LetMeDoWith.LetMeDoWith.config.TestQueryDslConfig;
import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberSocialAccount;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberType;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
@Import(TestQueryDslConfig.class)
class MemberRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private MemberSocialAccountRepository memberSocialAccountRepository;
    
    @BeforeEach
    void beforeEach() {
        entityManager.clear();
    }
    
    
    @Test
    @DisplayName("새로운 객체 삽입 테스트")
    void test_insert_new() {
        Member testMemberObj = Member.builder()
                                     .email("test@email.com")
                                     .nickname("nickname")
                                     .selfDescription("self desc")
                                     .status(MemberStatus.NORMAL)
                                     .type(MemberType.USER)
                                     .profileImageUrl("image.jpeg")
                                     .build();
        
        memberRepository.save(testMemberObj);
        
        Optional<Member> user = memberRepository.findByNickname("nickname");
        
        assertInstanceOf(Member.class, user.get());
        assertEquals(user.get().getNickname(), "nickname");
        assertEquals(user.get().getStatus(), MemberStatus.NORMAL);
    }
    
    @Test
    @DisplayName("객체 삭제 테스트")
    void test_delete() {
        Member testMemberObj = Member.builder()
                                     .email("test@email.com")
                                     .nickname("nickname")
                                     .selfDescription("self desc")
                                     .status(MemberStatus.NORMAL)
                                     .type(MemberType.USER)
                                     .profileImageUrl("image.jpeg")
                                     .build();
        
        memberRepository.save(testMemberObj);
        memberRepository.flush();
        
        Optional<Member> user = memberRepository.findByNickname("nickname");
        
        assertInstanceOf(Member.class, user.get());
        assertEquals(user.get()
                         .getNickname(), "nickname");
        
        // delete
        memberRepository.deleteAll();
        Optional<Member> userAfterDelete = memberRepository.findByNickname("nickname");
        
        Assertions.assertThrows(NoSuchElementException.class, userAfterDelete::get);
    }
    
    @Test
    void test_duplicated_key() {
        
        Member testMemberObj = Member.builder()
                                     .id(1L)
                                     .email("test@email.com")
                                     .nickname("nickname")
                                     .selfDescription("self desc")
                                     .status(MemberStatus.NORMAL)
                                     .type(MemberType.USER)
                                     .profileImageUrl("image.jpeg")
                                     .build();
        
        Member testMemberObjWithoutKey = Member.builder()
                                               .email("test@email.com")
                                               .nickname("nickname2")
                                               .selfDescription("self desc")
                                               .status(MemberStatus.NORMAL)
                                               .type(MemberType.USER)
                                               .profileImageUrl("image.jpeg")
                                               .build();
        
        memberRepository.save(testMemberObj);
        memberRepository.save(testMemberObjWithoutKey);
        
        Optional<Member> userWithCustomKey = memberRepository.findByNickname("nickname");
        Optional<Member> userWithDefaultKey = memberRepository.findByNickname("nickname2");
        
        assertNotEquals(testMemberObj.getId(), testMemberObjWithoutKey.getId());
    }
    
    @Test
    @DisplayName("[SUCCESS] 가입된 유저 조회 테스트")
    void findRegisteredMemberTest() {
        
        Member testMemberObj = Member.builder()
                                     .id(1L)
                                     .email("test@email.com")
                                     .nickname("nickname")
                                     .selfDescription("self desc")
                                     .status(MemberStatus.NORMAL)
                                     .type(MemberType.USER)
                                     .profileImageUrl("image.jpeg")
                                     .build();
        
        Member member = memberRepository.save(testMemberObj);
        
        MemberSocialAccount memberSocialAccount = MemberSocialAccount.builder()
                                                                     .provider(SocialProvider.KAKAO)
                                                                     .member(member)
                                                                     .build();
        
        memberSocialAccountRepository.save(memberSocialAccount);
        
        Optional<Member> optionalMember = memberRepository.findByProviderAndEmail(SocialProvider.KAKAO,
                                                                                  "test@email.com");
        log.debug("member= {}", optionalMember.get().getNickname());
        assertNotNull(optionalMember);
        assertEquals(optionalMember.get().getNickname(), "nickname");
    }
}