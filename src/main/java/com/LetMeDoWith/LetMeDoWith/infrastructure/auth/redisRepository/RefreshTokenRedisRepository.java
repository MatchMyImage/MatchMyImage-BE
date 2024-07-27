package com.LetMeDoWith.LetMeDoWith.infrastructure.auth.redisRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.LetMeDoWith.LetMeDoWith.application.auth.domain.RefreshToken;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

}
