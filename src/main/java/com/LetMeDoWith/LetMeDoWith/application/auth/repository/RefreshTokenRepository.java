package com.LetMeDoWith.LetMeDoWith.application.auth.repository;

import java.util.Optional;

import com.LetMeDoWith.LetMeDoWith.domain.auth.RefreshToken;

public interface RefreshTokenRepository {

	Optional<RefreshToken> getRefreshToken(String refreshToken);

	void deleteRefreshToken(RefreshToken refreshToken);
	void deleteRefreshTokens(Long memberId);

}
