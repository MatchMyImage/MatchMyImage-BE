package com.LetMeDoWith.LetMeDoWith.application.auth.repository;

import java.util.Optional;

import com.LetMeDoWith.LetMeDoWith.application.auth.domain.RefreshToken;

public interface RefreshTokenRepository {

	Optional<RefreshToken> getRefreshToken(String refreshToken);
	void delete(RefreshToken refreshToken);

}
