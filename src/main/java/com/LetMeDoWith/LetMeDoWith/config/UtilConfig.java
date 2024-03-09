package com.LetMeDoWith.LetMeDoWith.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.LetMeDoWith.LetMeDoWith.util.AuthTokenUtil;

@Component
public class UtilConfig {

	@Value("${auth.jwt.secret}")
	private String secret;

	@Bean
	public AuthTokenUtil authTokenUtil() {
		return new AuthTokenUtil(secret);
	}
}
