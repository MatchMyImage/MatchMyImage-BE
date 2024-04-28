package com.LetMeDoWith.LetMeDoWith.service;

import com.LetMeDoWith.LetMeDoWith.client.AuthClient;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialProviderAuthFactoryService {
	
	private final ApplicationContext applicationContext;
	
	public AuthClient getClient(SocialProvider socialProvider) {
		
		return (AuthClient) applicationContext.getBean(
			socialProvider.getCode().toLowerCase() + "AuthClient");
		
	}
}