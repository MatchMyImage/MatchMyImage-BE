package com.LetMeDoWith.LetMeDoWith.presentation.auth.dto;

import com.LetMeDoWith.LetMeDoWith.application.auth.dto.AuthTokenVO;
import com.LetMeDoWith.LetMeDoWith.domain.auth.RefreshToken;
import lombok.Builder;

@Builder
public record CreateTokenResDto(AuthTokenVO atk, RefreshToken rtk, AuthTokenVO signupToken) {

}