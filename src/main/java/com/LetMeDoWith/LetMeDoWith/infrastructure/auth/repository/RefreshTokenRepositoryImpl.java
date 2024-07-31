package com.LetMeDoWith.LetMeDoWith.infrastructure.auth.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.LetMeDoWith.LetMeDoWith.domain.auth.RefreshToken;
import com.LetMeDoWith.LetMeDoWith.application.auth.repository.RefreshTokenRepository;
import com.LetMeDoWith.LetMeDoWith.infrastructure.auth.redisRepository.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

	private final RefreshTokenRedisRepository refreshTokenRedisRepository;

	@Override
	public Optional<RefreshToken> getRefreshToken(String refreshToken) {
		return refreshTokenRedisRepository.findById(refreshToken);
	}

	@Override
	public void delete(RefreshToken refreshToken) {
		refreshTokenRedisRepository.delete(refreshToken);
	}
}
