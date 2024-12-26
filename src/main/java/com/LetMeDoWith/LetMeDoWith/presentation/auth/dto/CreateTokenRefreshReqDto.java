package com.LetMeDoWith.LetMeDoWith.presentation.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CreateTokenRefreshReqDto(String refreshToken) {
}
