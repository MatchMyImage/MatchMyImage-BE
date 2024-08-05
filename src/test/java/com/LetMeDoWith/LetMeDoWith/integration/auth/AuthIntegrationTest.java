package com.LetMeDoWith.LetMeDoWith.integration.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.LetMeDoWith.LetMeDoWith.application.auth.dto.AuthTokenVO;
import com.LetMeDoWith.LetMeDoWith.application.auth.provider.AuthTokenProvider;
import com.LetMeDoWith.LetMeDoWith.application.auth.service.AuthService;
import com.LetMeDoWith.LetMeDoWith.application.member.repository.MemberRepository;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.Gender;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.MemberType;
import com.LetMeDoWith.LetMeDoWith.domain.auth.RefreshToken;
import com.LetMeDoWith.LetMeDoWith.domain.member.Member;
import com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository.MemberJpaRepository;
import com.LetMeDoWith.LetMeDoWith.presentation.auth.dto.CreateTokenRefreshReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
public class AuthIntegrationTest {

	static final String BASE_URL = "/api/v1/auth";
	static final String TOKEN_REFRESH_URL = "/token/refresh";

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	AuthTokenProvider authTokenProvider;

	@Autowired
	AuthService authService;

	@Autowired
	MemberJpaRepository memberJpaRepository;

	Member member;

	static String userAgent = "IPHONE";

	@BeforeEach
	void beforeEach() {

		member = Member.builder()
			.status(MemberStatus.NORMAL)
			.nickname("test")
			.selfDescription("test description")
			.gender(Gender.MALE)
			.dateOfBirth(LocalDate.of(1995,11,4))
			.type(MemberType.USER)
			.build();

		memberJpaRepository.save(member);

	}

	@AfterEach
	void afterEach() {

		// memberJpaRepository.delete(member);

	}

	private ResultActions requestTokenRefresh(String accessToken, CreateTokenRefreshReqDto requestBody) throws  Exception {

		MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
		headerMap.add("AUTHORIZATION", "Bearer" + accessToken);
		headerMap.add("User-Agent", userAgent);

		return mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + TOKEN_REFRESH_URL)
				.headers(new HttpHeaders(headerMap))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.content(objectMapper.writeValueAsString(requestBody)))
			.andDo(System.out::println);

	}

	@Test
	@DisplayName("[SUCCESS] 토큰 재발급")
	void refreshTokenSuccessTest() throws Exception {

		// given
		AuthTokenVO accessTokenVo = authTokenProvider.createAccessToken(member.getId());
		RefreshToken refreshToken = authTokenProvider.createRefreshToken(member.getId(), accessTokenVo.token(), userAgent);
		CreateTokenRefreshReqDto requestBody = CreateTokenRefreshReqDto.builder()
			.refreshToken(refreshToken.getToken())
			.build();
		log.info("init accessToken = {}", accessTokenVo.token());
		log.info("init refreshToken = {}", refreshToken.getToken());

		// when
		ResultActions resultActions = requestTokenRefresh(accessTokenVo.token(), requestBody);

		// then
		resultActions.andExpect(status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.atk.token").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.rtk.token").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.atk.expireAt").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.rtk.expireAt").exists())
			.andDo(System.out::println);

	}

}
