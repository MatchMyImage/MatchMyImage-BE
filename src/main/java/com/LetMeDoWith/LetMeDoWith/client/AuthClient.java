package com.LetMeDoWith.LetMeDoWith.client;

import com.LetMeDoWith.LetMeDoWith.dto.responseDto.OidcPublicKeyResDto;
import com.LetMeDoWith.LetMeDoWith.dto.valueObject.OidcPublicKeyVO;

public interface AuthClient {
    
    OidcPublicKeyResDto getPublicKeyList();
    
    OidcPublicKeyVO getPublicKey(String kid);
}