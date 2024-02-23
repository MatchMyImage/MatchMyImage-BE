package com.LetMeDoWith.LetMeDoWith.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.LetMeDoWith.LetMeDoWith.client.AuthClient;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthFactoryService {

	private final ApplicationContext applicationContext;

	public AuthClient testMethod(SocialProvider socialProvider, String email) {

		AuthClient authClient = (AuthClient) applicationContext.getBean(socialProvider.getCode().toLowerCase() + "AuthClient");

		return authClient;
	}
}
