package com.LetMeDoWith.LetMeDoWith.dto.responseDto.client;

import java.util.List;
import lombok.Builder;

@Builder
public record OidcPublicKeyResDto(List<OidcPublicKeyVO> keys) {
    
    
    public record OidcPublicKeyVO(String kid,
                                  String alg,
                                  String use,
                                  String kty,
                                  String n,
                                  String e) {
        
    }
}