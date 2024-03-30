package com.LetMeDoWith.LetMeDoWith.client;

import com.LetMeDoWith.LetMeDoWith.dto.responseDto.OidcPublicKeyResDto;
import com.LetMeDoWith.LetMeDoWith.dto.valueObject.OidcPublicKeyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AppleAuthClient implements AuthClient {
    
    private final WebClient webClient;
    
    // TODO: 애플 OIDC id token 공개키 API URL 찾아서 넣을 것.
    @Override
    public OidcPublicKeyResDto getPublicKeyList() {
        return webClient.get()
                        .uri("TBD")
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