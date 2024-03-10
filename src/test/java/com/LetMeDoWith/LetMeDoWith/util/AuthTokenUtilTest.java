package com.LetMeDoWith.LetMeDoWith.util;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthTokenUtilTest {

	@Autowired
	private AuthTokenUtil authTokenUtil;

	private static String MEMBER_ID = "1";


	@Test
	@DisplayName("Access Token 발급 후 검증 성공")
	void validateAccessToken() {

		// given
		Map<Object, Object> accessTokenDto = authTokenUtil.createAccessToken(MEMBER_ID);
		System.out.println(accessTokenDto.get("token"));

		// when
		String memberId = authTokenUtil.validateAccessToken((String)accessTokenDto.get("token"));

		// then
		Assertions.assertThat(memberId).isEqualTo(MEMBER_ID);

	}
}
