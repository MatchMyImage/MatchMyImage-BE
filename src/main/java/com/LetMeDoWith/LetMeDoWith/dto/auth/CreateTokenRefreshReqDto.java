package com.LetMeDoWith.LetMeDoWith.dto.auth;

import lombok.Builder;

@Builder
public record CreateTokenRefreshReqDto(String accessToken, String refreshToken) {
}
