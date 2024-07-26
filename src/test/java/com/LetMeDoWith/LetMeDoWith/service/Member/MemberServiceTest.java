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

import com.LetMeDoWith.LetMeDoWith.dto.command.CreateSignupCompletedMemberCommand;
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
                                     .subject("test@email.com")
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
        
        when(memberRepository.findByProviderAndSubject(any(SocialProvider.class), anyString()))
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
                                      .subject("test@email.com")
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
                              .subject("test@email.com")
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
        
        assertEquals(createdTemporalMember.getSubject(), "test@email.com");
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
        String nickname = "newNickname";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        Gender gender = Gender.MALE;
        boolean isTerms = true;
        boolean isPrivacy = true;
        boolean isAdvertisement = true;
        
        CreateSignupCompletedMemberCommand command =
            CreateSignupCompletedMemberCommand.builder()
                                              .nickname(nickname)
                                              .dateOfBirth(dateOfBirth)
                                              .gender(gender)
                                              .isTerms(isTerms)
                                              .isPrivacy(isPrivacy)
                                              .isAdvertisement(isAdvertisement)
                                              .build();
        
        Member existingMember = Member.builder()
                                      .id(1L)
                                      .subject("test@email.com")
                                      .nickname("nickname")
                                      .selfDescription("self desc")
                                      .status(MemberStatus.SOCIAL_AUTHENTICATED)
                                      .type(MemberType.USER)
                                      .profileImageUrl("image.jpeg")
                                      .build();
        
        try (MockedStatic<AuthUtil> mockedAuthUtil = mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(AuthUtil::getMemberId).thenReturn(memberId);
            
            when(memberRepository.findById(1L))
                .thenReturn(Optional.of(existingMember));
            
            when(memberRepository.save(any(Member.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
            
            Member updatedMember = memberService.createSignupCompletedMember(command);
            
            assertNotNull(updatedMember);
            assertEquals("newNickname", updatedMember.getNickname());
            assertEquals(LocalDate.of(1990, 1, 1), updatedMember.getDateOfBirth());
            assertEquals(Gender.MALE, updatedMember.getGender());
            assertEquals(MemberStatus.NORMAL, updatedMember.getStatus());
            assertEquals("self desc", updatedMember.getSelfDescription());
            assertEquals(MemberType.USER, updatedMember.getType());
            assertEquals("image.jpeg", updatedMember.getProfileImageUrl());
        }
    }
    
    
    @Test
    @DisplayName("[SUCCESS] 회원의 약관 동의 정보를 생성")
    void createMemberTermAgreeTest() {
        try (MockedStatic<AuthUtil> mockedAuthUtil = mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(AuthUtil::getMemberId).thenReturn(memberId);
            
            boolean isTerms = true;
            boolean isPrivacy = true;
            boolean isAdvertisement = true;
            
            Member existingMember = Member.builder()
                                          .id(memberId)
                                          .subject("test@email.com")
                                          .nickname("nickname")
                                          .selfDescription("self desc")
                                          .status(MemberStatus.NORMAL)
                                          .type(MemberType.USER)
                                          .profileImageUrl("image.jpeg")
                                          .build();
            
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));
            
            memberService.createMemberTermAgree(isTerms, isPrivacy, isAdvertisement);
            
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
    @DisplayName("[SUCCESS] 사용 가능한 닉네임")
    public void isAvailableNicknameExistingTest() {
        when(memberRepository.findByNickname("availableNickname")).thenReturn(Optional.empty());
        
        boolean result = memberService.isExistingNickname("availableNickname");
        assertFalse(result);
    }
    
    @Test
    @DisplayName("[SUCCESS] 닉네임 앞뒤에 공백 포함")
    public void isAvailableNicknameTrimmedExistingTest() {
        when(memberRepository.findByNickname("trimmedNickname")).thenReturn(Optional.empty());
        
        boolean result = memberService.isExistingNickname("  trimmedNickname  ");
        assertFalse(result);
    }
    
    @Test
    @DisplayName("[FAIL] 유저 자체가 존재하지 않는 경우")
    void findNotRegisteredMemberTest() {
        when(memberRepository.findByProviderAndSubject(any(SocialProvider.class), anyString()))
            .thenReturn(Optional.empty());
        
        assertFalse(memberService.getRegisteredMember(SocialProvider.GOOGLE, "email").isPresent());
    }
    
    @Test
    @DisplayName("[FAIL] 이메일과 연결된 social provider가 없는 경우")
    void findNotRelatedProviderMemberTest() {
        Member testMemberObj = Member.builder()
                                     .subject("test@email.com")
                                     .nickname("nickname")
                                     .selfDescription("self desc")
                                     .status(MemberStatus.NORMAL)
                                     .type(MemberType.USER)
                                     .profileImageUrl("image.jpeg")
                                     .build();
        
        when(memberRepository.findByProviderAndSubject(eq(SocialProvider.KAKAO),
                                                       eq("test@email.com")))
            .thenReturn(Optional.of(testMemberObj));
        when(memberRepository.findByProviderAndSubject(eq(SocialProvider.GOOGLE),
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
        String nickname = "newNickname";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        Gender gender = Gender.MALE;
        boolean isTerms = true;
        boolean isPrivacy = true;
        boolean isAdvertisement = true;
        
        CreateSignupCompletedMemberCommand command =
            CreateSignupCompletedMemberCommand.builder()
                                              .nickname(nickname)
                                              .dateOfBirth(dateOfBirth)
                                              .gender(gender)
                                              .isTerms(isTerms)
                                              .isPrivacy(isPrivacy)
                                              .isAdvertisement(isAdvertisement)
                                              .build();
        
        try (MockedStatic<AuthUtil> mockedAuthUtil = mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(AuthUtil::getMemberId)
                          .thenThrow(new RestApiException(FailResponseStatus.INVALID_TOKEN));
            
            RestApiException exception = assertThrows(
                RestApiException.class,
                () -> memberService.createSignupCompletedMember(command)
            );
            
            assertEquals(FailResponseStatus.INVALID_TOKEN, exception.getStatus());
        }
    }
    
    @Test
    @DisplayName("[FAIL] 유효하지 않은 멤버 ID로 회원가입 완료 요청")
    void createSignupCompletedMemberInvalidMemberIdTest() {
        String nickname = "newNickname";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        Gender gender = Gender.MALE;
        boolean isTerms = true;
        boolean isPrivacy = true;
        boolean isAdvertisement = true;
        
        CreateSignupCompletedMemberCommand command =
            CreateSignupCompletedMemberCommand.builder()
                                              .nickname(nickname)
                                              .dateOfBirth(dateOfBirth)
                                              .gender(gender)
                                              .isTerms(isTerms)
                                              .isPrivacy(isPrivacy)
                                              .isAdvertisement(isAdvertisement)
                                              .build();
        
        try (MockedStatic<AuthUtil> mockedAuthUtil = mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(AuthUtil::getMemberId).thenReturn(1L);
            
            when(memberRepository.findById(1L))
                .thenReturn(Optional.empty());
            
            RestApiException exception = assertThrows(
                RestApiException.class,
                () -> memberService.createSignupCompletedMember(command)
            );
            
            assertEquals(FailResponseStatus.INVALID_TOKEN, exception.getStatus());
        }
    }
    
    @Test
    @DisplayName("[FAIL] 중복된 닉네임으로 회원가입 완료 요청")
    public void createSignupCompletedMemberDuplicateNicknameTest() {
        String nickname = "duplicateNickname";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        Gender gender = Gender.MALE;
        boolean isTerms = true;
        boolean isPrivacy = true;
        boolean isAdvertisement = true;
        
        CreateSignupCompletedMemberCommand command =
            CreateSignupCompletedMemberCommand.builder()
                                              .nickname(nickname)
                                              .dateOfBirth(dateOfBirth)
                                              .gender(gender)
                                              .isTerms(isTerms)
                                              .isPrivacy(isPrivacy)
                                              .isAdvertisement(isAdvertisement)
                                              .build();
        
        try (MockedStatic<AuthUtil> mockedAuthUtil = mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(AuthUtil::getMemberId).thenReturn(1L);
            Member mockMember = Member.initialize("sampleSubject");
            
            when(memberRepository.findById(1L)).thenReturn(Optional.of(mockMember));
            when(memberRepository.findByNickname("duplicateNickname")).thenReturn(Optional.of(new Member()));
            
            RestApiException exception = assertThrows(RestApiException.class, () -> {
                memberService.createSignupCompletedMember(command);
            });
            
            assertEquals(FailResponseStatus.DUPLICATE_NICKNAME, exception.getStatus());
        }
    }
    
    @Test
    @DisplayName("[FAIL] 필수 동의 항목이 false인 경우 예외 발생")
    void createMemberTermAgreeFailDueToInvalidParamsTest() {
        try (MockedStatic<AuthUtil> mockedAuthUtil = mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(AuthUtil::getMemberId).thenReturn(memberId);
            
            boolean isTerms = false;
            boolean isPrivacy = true;
            boolean isAdvertisement = true;
            
            Member existingMember = Member.builder()
                                          .id(memberId)
                                          .subject("test@email.com")
                                          .nickname("nickname")
                                          .selfDescription("self desc")
                                          .status(MemberStatus.NORMAL)
                                          .type(MemberType.USER)
                                          .profileImageUrl("image.jpeg")
                                          .build();
            
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));
            
            RestApiException exception = assertThrows(
                RestApiException.class,
                () -> memberService.createMemberTermAgree(isTerms, isPrivacy, isAdvertisement)
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
            
            boolean isTerms = false;
            boolean isPrivacy = true;
            boolean isAdvertisement = true;
            
            when(memberRepository.findById(memberId)).thenReturn(Optional.empty());
            
            RestApiException exception = assertThrows(
                RestApiException.class,
                () -> memberService.createMemberTermAgree(isTerms, isPrivacy, isAdvertisement)
            );
            
            assertEquals(FailResponseStatus.MEMBER_NOT_EXIST, exception.getStatus());
            
            // 검증: save가 호출되지 않았는지 확인
            verify(memberTermAgreeRepository, never()).save(any(MemberTermAgree.class));
        }
    }
    
    @Test
    @DisplayName("[FAIL] 이미 사용중인 닉네임")
    public void isExistingNicknameTakenTest() {
        when(memberRepository.findByNickname("takenNickname")).thenReturn(Optional.of(new Member()));
        
        boolean result = memberService.isExistingNickname("takenNickname");
        assertTrue(result);
    }
    
    @Test
    @DisplayName("[FAIL] 닉네임 앞뒤에 공백 포함된 이미 사용중인 닉네임")
    public void isExistingNicknameTrimmedTakenTest() {
        when(memberRepository.findByNickname("trimmedNickname")).thenReturn(Optional.of(new Member()));
        
        boolean result = memberService.isExistingNickname("  trimmedNickname  ");
        assertTrue(result);
    }
    
    @Test
    @DisplayName("[FAIL] 닉네임이 비어있거나 공백으로만 이루어짐")
    public void isExistingNicknameBlankOrWhitespaceTest() {
        assertThrows(RestApiException.class, () -> {
            memberService.isExistingNickname("");
        });
        
        assertThrows(RestApiException.class, () -> {
            memberService.isExistingNickname("   ");
        });
    }
}