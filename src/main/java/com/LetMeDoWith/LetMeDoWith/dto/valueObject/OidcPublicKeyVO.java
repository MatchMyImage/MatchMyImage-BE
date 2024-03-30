package com.LetMeDoWith.LetMeDoWith.dto.valueObject;

public record OidcPublicKeyVO(String kid,
                              String alg,
                              String use,
                              String kty,
                              String n,
                              String e) {
    
}