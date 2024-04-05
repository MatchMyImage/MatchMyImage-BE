package com.LetMeDoWith.LetMeDoWith.client;

import com.LetMeDoWith.LetMeDoWith.dto.responseDto.OidcPublicKeyResDto;
import reactor.core.publisher.Mono;

public interface AuthClient {
    
    Mono<OidcPublicKeyResDto> getPublicKeyList();
}