package com.LetMeDoWith.LetMeDoWith.client;

import com.LetMeDoWith.LetMeDoWith.dto.responseDto.OidcPublicKeyResDto;
import reactor.core.publisher.Mono;

public interface AuthClient {
    
    // TODO: Public key 캐싱 주기에 대한 설정 필요
    Mono<OidcPublicKeyResDto> getPublicKeyList();
}