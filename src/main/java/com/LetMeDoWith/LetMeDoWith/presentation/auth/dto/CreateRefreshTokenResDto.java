package com.LetMeDoWith.LetMeDoWith.presentation.auth.dto;

import com.LetMeDoWith.LetMeDoWith.application.auth.dto.CreateRefreshTokenResult;
import com.LetMeDoWith.LetMeDoWith.domain.auth.model.AccessToken;
import java.time.LocalDateTime;

import com.LetMeDoWith.LetMeDoWith.domain.auth.model.RefreshToken;
import lombok.Builder;

@Builder
public record CreateRefreshTokenResDto(
	AccessTokenDto atk,
	RefreshTokenDto rtk
) {

	@Builder
	public static record AccessTokenDto(
		String token,
		LocalDateTime expireAt
	) {
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

	public static CreateRefreshTokenResDto of(CreateRefreshTokenResult result) {
		return CreateRefreshTokenResDto.builder()
				.atk(AccessTokenDto.from(result.accessToken()))
				.rtk(RefreshTokenDto.from(result.refreshToken()))
				.build();
	}
}
