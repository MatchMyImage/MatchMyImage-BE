package com.LetMeDoWith.LetMeDoWith.presentation.auth.dto;

import java.time.LocalDateTime;

import com.LetMeDoWith.LetMeDoWith.application.auth.dto.AuthTokenVO;
import com.LetMeDoWith.LetMeDoWith.domain.auth.RefreshToken;
import lombok.Builder;

@Builder
public record CreateTokenRefreshResDto(
	AccessTokenDto accessToken,
	RefreshTokenDto refreshToken
) {

	@Builder
	public static record AccessTokenDto(
		String token,
		LocalDateTime expireAt
	) {
		public static AccessTokenDto from(AuthTokenVO authTokenVO) {
			return new AccessTokenDto(authTokenVO.token(), authTokenVO.expireAt());
		}
	}

	@Builder
	public static record RefreshTokenDto(
		String token,
		LocalDateTime expireAt
	) {
		public static RefreshTokenDto from(RefreshToken refreshToken) {
			return new RefreshTokenDto(refreshToken.getToken(), refreshToken.calculateExpireAt());
		}
	}
}
