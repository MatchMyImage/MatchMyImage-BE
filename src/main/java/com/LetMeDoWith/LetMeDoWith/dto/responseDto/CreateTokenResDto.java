package com.LetMeDoWith.LetMeDoWith.dto.responseDto;

import com.LetMeDoWith.LetMeDoWith.dto.valueObject.AuthTokenVO;
import com.LetMeDoWith.LetMeDoWith.entity.auth.RefreshToken;
import lombok.Builder;

@Builder
public record CreateTokenResDto(AuthTokenVO atk, RefreshToken rtk, AuthTokenVO signupToken) {

}