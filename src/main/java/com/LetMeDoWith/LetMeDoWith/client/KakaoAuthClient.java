package com.LetMeDoWith.LetMeDoWith.client;

import com.LetMeDoWith.LetMeDoWith.dto.responseDto.OidcPublicKeyResDto;
import com.LetMeDoWith.LetMeDoWith.dto.valueObject.OidcPublicKeyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class KakaoAuthClient implements AuthClient {
    
    private final WebClient webClient;
    
    @Override
    public OidcPublicKeyResDto getPublicKeyList() {
        return webClient.get()
                        .uri("https://kauth.kakao.com/.well-known/jwks.json")
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(OidcPublicKeyResDto.class)
                        .block();
    }
    
    @Override
    public OidcPublicKeyVO getPublicKey(String kid) {
        return getPublicKeyList().keys()
                                 .stream()
                                 .filter(key -> key.kid().equals(kid))
                                 .findFirst()
                                 .orElseThrow(IllegalArgumentException::new);
    }
}