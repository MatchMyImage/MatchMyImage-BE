package com.LetMeDoWith.LetMeDoWith.application.auth.service;

import static org.assertj.core.api.Assertions.*;

import com.LetMeDoWith.LetMeDoWith.application.auth.provider.AuthTokenProvider;
import com.LetMeDoWith.LetMeDoWith.infrastructure.auth.redisRepository.RefreshTokenRedisRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class AuthTokenProviderTest {

  @Mock
  RefreshTokenRedisRepository refreshTokenRedisRepository;

  AuthTokenProvider authTokenProvider = new AuthTokenProvider("sdfdasfsaffdfdslfjldsfkadjldfadfdsafdasfdsfdflkjadlkfdjkfdjlflasjfjdfaflajlkfjldksjfladjdsafkljfdjflasf", refreshTokenRedisRepository);

  @Test
  void getMemberIdWithoutVerifyTest() {
    // given
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJsZXRNZURvV2l0aC5jby5rciIsImlhdCI6MTcyNTI4MjY2MCwiZXhwIjoxNzI1MjkzNDYwLCJzdWIiOiJBVEsiLCJtZW1iZXJJZCI6MTU4fQ.WPHauDcEsucLtdoKjEFilG6hf4XadvDG7jdIIKG2USoLf5UqMu4yozoAvgSfPTZmO_q3-UZ-Xk6utC7qav3ZjA";

    // when
    Long memberIdWithoutVerify = authTokenProvider.getMemberIdWithoutVerify(token);

    // then
    assertThat(memberIdWithoutVerify).isEqualTo(158);

  }

}
