package com.LetMeDoWith.LetMeDoWith.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.LetMeDoWith.LetMeDoWith.client.AuthClient;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocialProviderAuthFactoryService {

	private final ApplicationContext applicationContext;

	private AuthClient getClient(SocialProvider socialProvider) {

		return (AuthClient) applicationContext.getBean(socialProvider.getCode().toLowerCase() + "AuthClient");

	}
}
