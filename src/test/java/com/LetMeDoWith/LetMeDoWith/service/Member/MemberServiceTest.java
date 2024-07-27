package com.LetMeDoWith.LetMeDoWith.service.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.LetMeDoWith.LetMeDoWith.application.member.domain.Member;
import com.LetMeDoWith.LetMeDoWith.application.member.domain.MemberSocialAccount;
import com.LetMeDoWith.LetMeDoWith.application.member.service.MemberService;
import com.LetMeDoWith.LetMeDoWith.common.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.MemberType;
import com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository.MemberJpaRepository;
import com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository.MemberSocialAccountJpaRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    
    @Mock
    MemberJpaRepository memberJpaRepository;
    
    @Mock
    MemberSocialAccountJpaRepository memberSocialAccountJpaRepository;
    
    @InjectMocks
    MemberService memberService;
    
    @Test
    @DisplayName("[SUCCESS] 기 가입 유저 조회")
    void findAlreadyRegisteredMemberTest() {
        Member testMemberObj = Member.builder()
                                     .email("test@email.com")
                                     .nickname("nickname")
                                     .selfDescription("self desc")
                                     .status(MemberStatus.NORMAL)
                                     .type(MemberType.USER)
                                     .profileImageUrl("image.jpeg")
                                     .build();
        
        MemberSocialAccount memberSocialAccountKaKao = MemberSocialAccount.builder()
                                                                          .member(testMemberObj)
                                                                          .provider(SocialProvider.KAKAO)
                                                                          .build();
        
        when(memberJpaRepository.findByProviderAndEmail(any(SocialProvider.class), anyString()))
            .thenReturn(Optional.of(testMemberObj));
        
        Optional<Member> registeredMember = memberService.getRegisteredMember(SocialProvider.KAKAO,
                                                                              "test@email.com");
        
        assertTrue(registeredMember.isPresent());
        assertEquals(registeredMember.get().getNickname(), "nickname");
    }
    
    @Test
    @DisplayName("[SUCCESS] 임시 멤버 생성")
    void createSocialAuthenticatedMemberTest() {
        Member temporalMember = Member.builder()
                                      .email("test@email.com")
                                      .type(MemberType.USER)
                                      .status(MemberStatus.SOCIAL_AUTHENTICATED)
                                      .build();
        
        MemberSocialAccount temporalSocialAccount = MemberSocialAccount.builder()
                                                                       .member(temporalMember)
                                                                       .provider(SocialProvider.KAKAO)
                                                                       .build();
        
        when(memberJpaRepository.save(any(Member.class)))
            .thenReturn(Member.builder()
                              .id(1L)
                              .email("test@email.com")
                              .type(MemberType.USER)
                              .status(MemberStatus.SOCIAL_AUTHENTICATED)
                              .socialAccountList(new ArrayList<>())
                              .statusHistoryList(new ArrayList<>())
                              .selfDescription("")
                              .profileImageUrl("")
                              .build());
        
        Member createdTemporalMember = memberService.createSocialAuthenticatedMember(
            SocialProvider.KAKAO,
            "test@email.com");
        
        assertEquals(createdTemporalMember.getEmail(), "test@email.com");
        assertEquals(createdTemporalMember.getStatus(), MemberStatus.SOCIAL_AUTHENTICATED);
        
        assertTrue(
            createdTemporalMember.getSocialAccountList()
                                 .stream()
                                 .allMatch(account -> account.getProvider()
                                                             .equals(SocialProvider.KAKAO))
        );
    }
    
    @Test
    @DisplayName("[FAIL] 유저 자체가 존재하지 않는 경우")
    void findNotRegisteredMemberTest() {
        when(memberJpaRepository.findByProviderAndEmail(any(SocialProvider.class), anyString()))
            .thenReturn(Optional.empty());
        
        assertFalse(memberService.getRegisteredMember(SocialProvider.GOOGLE, "email").isPresent());
    }
    
    @Test
    @DisplayName("[FAIL] 이메일과 연결된 social provider가 없는 경우")
    void findNotRelatedProviderMemberTest() {
        Member testMemberObj = Member.builder()
                                     .email("test@email.com")
                                     .nickname("nickname")
                                     .selfDescription("self desc")
                                     .status(MemberStatus.NORMAL)
                                     .type(MemberType.USER)
                                     .profileImageUrl("image.jpeg")
                                     .build();
        
        when(memberJpaRepository.findByProviderAndEmail(eq(SocialProvider.KAKAO),
                                                     eq("test@email.com")))
            .thenReturn(Optional.of(testMemberObj));
        when(memberJpaRepository.findByProviderAndEmail(eq(SocialProvider.GOOGLE),
                                                     eq("test@email.com")))
            .thenReturn(Optional.empty());
        
        assertTrue(memberService.getRegisteredMember(SocialProvider.KAKAO, "test@email.com")
                                .isPresent());
        assertFalse(memberService.getRegisteredMember(SocialProvider.GOOGLE, "test@email.com")
                                 .isPresent());
        
        
    }
}