package com.LetMeDoWith.LetMeDoWith.service.Member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.LetMeDoWith.LetMeDoWith.dto.requestDto.CreateMemberTermAgreeReq;
import com.LetMeDoWith.LetMeDoWith.dto.requestDto.SignupCompleteReq;
import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberSocialAccount;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberTermAgree;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.Gender;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberType;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.provider.AuthTokenProvider;
import com.LetMeDoWith.LetMeDoWith.provider.AuthTokenProvider.TokenType;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberRepository;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberSocialAccountRepository;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberTermAgreeRepository;
import com.LetMeDoWith.LetMeDoWith.util.AuthUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class MemberServiceTest {
    
    private final Long memberId = 1L;
    @Mock
    MemberRepository memberRepository;
    @Mock
    MemberSocialAccountRepository memberSocialAccountRepository;
    @Mock
    MemberTermAgreeRepository memberTermAgreeRepository;
    @Mock
    AuthTokenProvider authTokenProvider;
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
        
        when(memberRepository.findByProviderAndEmail(any(SocialProvider.class), anyString()))
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
        
        when(memberRepository.save(any(Member.class)))
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
    @DisplayName("[SUCCESS] 회원가입 완료 멤버 업데이트")
    void createSignupCompletedMemberTest() {
        SignupCompleteReq signupCompleteReq = SignupCompleteReq.builder()
                                                               .signupToken("validSignupToken")
                                                               .nickname("newNickname")
                                                               .dateOfBirth(LocalDate.of(1990,
                                                                                         1,
                                                                                         1))
                                                               .gender(Gender.MALE)
                                                               .build();
        
        Member existingMember = Member.builder()
                                      .id(1L)
                                      .email("test@email.com")
                                      .nickname("nickname")
                                      .selfDescription("self desc")
                                      .status(MemberStatus.SOCIAL_AUTHENTICATED)
                                      .type(MemberType.USER)
                                      .profileImageUrl("image.jpeg")
                                      .build();
        
        when(authTokenProvider.validateToken("validSignupToken", TokenType.SIGNUP))
            .thenReturn(1L);
        
        when(memberRepository.findById(1L))
            .thenReturn(Optional.of(existingMember));
        
        when(memberRepository.save(any(Member.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
        
        Member updatedMember = memberService.createSignupCompletedMember(signupCompleteReq);
        
        assertNotNull(updatedMember);
        assertEquals("newNickname", updatedMember.getNickname());
        assertEquals(LocalDate.of(1990, 1, 1), updatedMember.getDateOfBirth());
        assertEquals(Gender.MALE, updatedMember.getGender());
        assertEquals(MemberStatus.NORMAL, updatedMember.getStatus());
        assertEquals("self desc", updatedMember.getSelfDescription());
        assertEquals(MemberType.USER, updatedMember.getType());
        assertEquals("image.jpeg", updatedMember.getProfileImageUrl());
    }
    
    
    @Test
    @DisplayName("[SUCCESS] 회원의 약관 동의 정보를 생성")
    void createMemberTermAgreeTest() {
        try (MockedStatic<AuthUtil> mockedAuthUtil = mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(AuthUtil::getMemberId).thenReturn(memberId);
            
            CreateMemberTermAgreeReq createMemberTermAgreeReq =
                CreateMemberTermAgreeReq.builder()
                                        .termsOfAgree(true)
                                        .privacy(true)
                                        .advertisement(true)
                                        .build();
            
            Member existingMember = Member.builder()
                                          .id(memberId)
                                          .email("test@email.com")
                                          .nickname("nickname")
                                          .selfDescription("self desc")
                                          .status(MemberStatus.NORMAL)
                                          .type(MemberType.USER)
                                          .profileImageUrl("image.jpeg")
                                          .build();
            
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));
            
            memberService.createMemberTermAgree(createMemberTermAgreeReq);
            
            // 메서드 호출 검증
            verify(memberRepository, times(1)).findById(memberId);
            
            ArgumentCaptor<MemberTermAgree> captor = ArgumentCaptor.forClass(MemberTermAgree.class);
            verify(memberTermAgreeRepository, times(1)).save(captor.capture());
            
            MemberTermAgree savedTermAgree = captor.getValue();
            assertTrue(savedTermAgree.isTermsOfAgree());
            assertTrue(savedTermAgree.isPrivacy());
            assertTrue(savedTermAgree.isAdvertisement());
            assertEquals(existingMember, savedTermAgree.getMember());
        }
    }
    
    @Test
    @DisplayName("[FAIL] 유저 자체가 존재하지 않는 경우")
    void findNotRegisteredMemberTest() {
        when(memberRepository.findByProviderAndEmail(any(SocialProvider.class), anyString()))
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
        
        when(memberRepository.findByProviderAndEmail(eq(SocialProvider.KAKAO),
                                                     eq("test@email.com")))
            .thenReturn(Optional.of(testMemberObj));
        when(memberRepository.findByProviderAndEmail(eq(SocialProvider.GOOGLE),
                                                     eq("test@email.com")))
            .thenReturn(Optional.empty());
        
        assertTrue(memberService.getRegisteredMember(SocialProvider.KAKAO, "test@email.com")
                                .isPresent());
        assertFalse(memberService.getRegisteredMember(SocialProvider.GOOGLE, "test@email.com")
                                 .isPresent());
        
        
    }
    
    @Test
    @DisplayName("[FAIL] 유효하지 않은 토큰으로 회원가입 완료 요청")
    void createSignupCompletedMemberInvalidTokenTest() {
        SignupCompleteReq signupCompleteReq = SignupCompleteReq.builder()
                                                               .signupToken("invalidSignupToken")
                                                               .nickname("newNickname")
                                                               .dateOfBirth(LocalDate.of(1990,
                                                                                         1,
                                                                                         1))
                                                               .gender(Gender.MALE)
                                                               .build();
        
        when(authTokenProvider.validateToken("invalidSignupToken", TokenType.SIGNUP))
            .thenThrow(new RestApiException(FailResponseStatus.INVALID_TOKEN));
        
        RestApiException exception = assertThrows(
            RestApiException.class,
            () -> memberService.createSignupCompletedMember(signupCompleteReq)
        );
        
        assertEquals(FailResponseStatus.INVALID_TOKEN, exception.getStatus());
    }
    
    @Test
    @DisplayName("[FAIL] 유효하지 않은 멤버 ID로 회원가입 완료 요청")
    void createSignupCompletedMemberInvalidMemberIdTest() {
        SignupCompleteReq signupCompleteReq = SignupCompleteReq.builder()
                                                               .signupToken("validSignupToken")
                                                               .nickname("newNickname")
                                                               .dateOfBirth(LocalDate.of(1990,
                                                                                         1,
                                                                                         1))
                                                               .gender(Gender.MALE)
                                                               .build();
        
        when(authTokenProvider.validateToken("validSignupToken", TokenType.SIGNUP))
            .thenReturn(1L);
        
        when(memberRepository.findById(1L))
            .thenReturn(Optional.empty());
        
        RestApiException exception = assertThrows(
            RestApiException.class,
            () -> memberService.createSignupCompletedMember(signupCompleteReq)
        );
        
        assertEquals(FailResponseStatus.INVALID_TOKEN, exception.getStatus());
    }
    
    @Test
    @DisplayName("[FAIL] 필수 동의 항목이 false인 경우 예외 발생")
    void createMemberTermAgreeFailDueToInvalidParamsTest() {
        try (MockedStatic<AuthUtil> mockedAuthUtil = mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(AuthUtil::getMemberId).thenReturn(memberId);
            
            CreateMemberTermAgreeReq createMemberTermAgreeReq =
                CreateMemberTermAgreeReq.builder()
                                        .termsOfAgree(false)
                                        .privacy(true)
                                        .advertisement(true)
                                        .build();
            
            Member existingMember = Member.builder()
                                          .id(memberId)
                                          .email("test@email.com")
                                          .nickname("nickname")
                                          .selfDescription("self desc")
                                          .status(MemberStatus.NORMAL)
                                          .type(MemberType.USER)
                                          .profileImageUrl("image.jpeg")
                                          .build();
            
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));
            
            RestApiException exception = assertThrows(
                RestApiException.class,
                () -> memberService.createMemberTermAgree(createMemberTermAgreeReq)
            );
            
            assertEquals(FailResponseStatus.INVALID_PARAM_ERROR, exception.getStatus());
            
            // 검증: save가 호출되지 않았는지 확인
            verify(memberTermAgreeRepository, never()).save(any(MemberTermAgree.class));
        }
    }
    
    @Test
    @DisplayName("[FAIL] 회원이 존재하지 않을 경우 예외 발생")
    void createMemberTermAgreeFailDueToMemberNotExistTest() {
        try (MockedStatic<AuthUtil> mockedAuthUtil = mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(AuthUtil::getMemberId).thenReturn(memberId);
            
            CreateMemberTermAgreeReq createMemberTermAgreeReq =
                CreateMemberTermAgreeReq.builder()
                                        .termsOfAgree(false)
                                        .privacy(true)
                                        .advertisement(true)
                                        .build();
            
            when(memberRepository.findById(memberId)).thenReturn(Optional.empty());
            
            RestApiException exception = assertThrows(
                RestApiException.class,
                () -> memberService.createMemberTermAgree(createMemberTermAgreeReq)
            );
            
            assertEquals(FailResponseStatus.MEMBER_NOT_EXIST, exception.getStatus());
            
            // 검증: save가 호출되지 않았는지 확인
            verify(memberTermAgreeRepository, never()).save(any(MemberTermAgree.class));
        }
    }
}