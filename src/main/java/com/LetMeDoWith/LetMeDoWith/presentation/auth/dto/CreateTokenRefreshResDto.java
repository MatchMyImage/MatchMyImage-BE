package com.LetMeDoWith.LetMeDoWith.presentation.auth.dto;

import com.LetMeDoWith.LetMeDoWith.domain.auth.model.AccessToken;
import java.time.LocalDateTime;

import com.LetMeDoWith.LetMeDoWith.application.auth.dto.AuthTokenVO;
import com.LetMeDoWith.LetMeDoWith.domain.auth.model.RefreshToken;
import lombok.Builder;

@Builder
public record CreateTokenRefreshResDto(
	AccessTokenDto atk,
	RefreshTokenDto rtk
) {

	@Builder
	public static record AccessTokenDto(
		String token,
		LocalDateTime expireAt
	) {
		public static AccessTokenDto from(AuthTokenVO authTokenVO) {
			return new AccessTokenDto(authTokenVO.token(), authTokenVO.expireAt());
		}
		public static AccessTokenDto from(AccessToken accessToken) {
			return new AccessTokenDto(accessToken.getToken(), accessToken.getExpireAt());
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

	public static CreateTokenRefreshResDto of(AccessToken accessToken, RefreshToken refreshToken) {
		return CreateTokenRefreshResDto.builder()
				.atk(AccessTokenDto.from(accessToken))
				.rtk(RefreshTokenDto.from(refreshToken))
				.build();
	}
}
