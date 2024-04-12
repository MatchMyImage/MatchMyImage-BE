package com.LetMeDoWith.LetMeDoWith.service.Member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberSocialAccount;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberType;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberRepository;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberSocialAccountRepository;
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
    MemberRepository memberRepository;
    
    @Mock
    MemberSocialAccountRepository memberSocialAccountRepository;
    
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
                                                                          .type(SocialProvider.KAKAO)
                                                                          .build();
        
        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(testMemberObj));
        when(memberSocialAccountRepository
                 .findByMemberAndType(any(Member.class),
                                      any(SocialProvider.class)))
            .thenReturn(Optional.of(memberSocialAccountKaKao));
        
        Member registeredMember = memberService.getRegisteredMember(SocialProvider.KAKAO,
                                                                    "test@email.com");
        
        assertNotNull(registeredMember);
        assertEquals(registeredMember.getNickname(), "nickname");
    }
    
    @Test
    @DisplayName("[FAIL] 유저 자체가 존재하지 않는 경우")
    void findNotRegisteredMemberTest() {
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        
        assertNull(memberService.getRegisteredMember(SocialProvider.GOOGLE, "email"));
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
        
        when(memberRepository.findByEmail(eq("test@email.com")))
            .thenReturn(Optional.of(testMemberObj));
        when(memberSocialAccountRepository.findByMemberAndType(eq(testMemberObj),
                                                               any(SocialProvider.class)))
            .thenReturn(Optional.empty());
        
        assertNull(memberService.getRegisteredMember(SocialProvider.KAKAO, "test@email.com"));
    }
}