package com.LetMeDoWith.LetMeDoWith.dto.auth;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record CreateTokenRefreshResDto(String accessToken, LocalDateTime accessTokenExpireAt, String refreshToken) {
}
