package com.LetMeDoWith.LetMeDoWith.dto.common;

import lombok.Builder;

@Builder
public record RefreshTokenDto(String refreshToken, String memberId, Long expireSec) {
}
