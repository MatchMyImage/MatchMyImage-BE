package com.LetMeDoWith.LetMeDoWith.application.auth.client;

import com.LetMeDoWith.LetMeDoWith.application.auth.dto.OidcPublicKeyResDto;
import reactor.core.publisher.Mono;

public interface AuthClient {
    
    // TODO: Public key 캐싱 주기에 대한 설정 필요
    Mono<OidcPublicKeyResDto> getPublicKeyList();
}