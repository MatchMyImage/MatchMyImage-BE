package com.LetMeDoWith.LetMeDoWith.presentation.auth.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record CreateTokenRefreshResDto(String accessToken, LocalDateTime accessTokenExpireAt, String refreshToken) {
}
