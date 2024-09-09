package com.LetMeDoWith.LetMeDoWith.application.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.LetMeDoWith.LetMeDoWith.application.auth.provider.AuthTokenProvider;
import com.LetMeDoWith.LetMeDoWith.application.auth.provider.OidcIdTokenProvider;
import com.LetMeDoWith.LetMeDoWith.application.auth.repository.RefreshTokenRepository;
import com.LetMeDoWith.LetMeDoWith.application.auth.service.AuthService;
import com.LetMeDoWith.LetMeDoWith.application.member.service.MemberService;
import com.LetMeDoWith.LetMeDoWith.domain.member.Member;

@ExtendWith({MockitoExtension.class})
public class AuthServiceTest {

	@Mock
	AuthTokenProvider authTokenProvider;

	@Mock
	OidcIdTokenProvider oidcIdTokenProvider;

	@Mock
	MemberService memberService;

	@Mock
	RefreshTokenRepository refreshTokenRepository;

	@InjectMocks
	AuthService authService;

	@Test
	@DisplayName("[SUCCESS] 토큰 재발급")
	void createTokenRefreshSuccessTest() {
		// TODO
	}

}
