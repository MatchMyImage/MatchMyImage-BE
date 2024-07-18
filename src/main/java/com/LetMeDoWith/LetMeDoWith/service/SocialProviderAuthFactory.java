package com.LetMeDoWith.LetMeDoWith.service;

import com.LetMeDoWith.LetMeDoWith.client.AuthClient;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialProviderAuthFactory {
    
    private final ApplicationContext applicationContext;
    
    public AuthClient getClient(SocialProvider socialProvider) {
        
        return (AuthClient) applicationContext.getBean(
            socialProvider.getCode().toLowerCase() + "AuthClient");
        
    }
}