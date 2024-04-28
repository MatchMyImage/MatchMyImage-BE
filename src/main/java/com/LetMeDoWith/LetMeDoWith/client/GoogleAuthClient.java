package com.LetMeDoWith.LetMeDoWith.client;

import com.LetMeDoWith.LetMeDoWith.dto.responseDto.client.OidcPublicKeyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GoogleAuthClient implements AuthClient {
    
    private final WebClient webClient;
    
    @Override
    @Cacheable(key = "'AuthPublicKey-Google'")
    public Mono<OidcPublicKeyResDto> getPublicKeyList() {
        return webClient.get()
                        .uri("https://www.googleapis.com/oauth2/v3/certs")
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(OidcPublicKeyResDto.class);
    }
}