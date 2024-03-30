package com.LetMeDoWith.LetMeDoWith.dto.responseDto;

import com.LetMeDoWith.LetMeDoWith.dto.valueObject.OidcPublicKeyVO;
import java.util.List;
import lombok.Builder;

@Builder
public record OidcPublicKeyResDto(List<OidcPublicKeyVO> keys) {
    

}