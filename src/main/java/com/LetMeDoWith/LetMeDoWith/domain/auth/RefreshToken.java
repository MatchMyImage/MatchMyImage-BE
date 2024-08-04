package com.LetMeDoWith.LetMeDoWith.domain.auth;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import com.LetMeDoWith.LetMeDoWith.common.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@RedisHash("refreshToken")
@AllArgsConstructor
@RequiredArgsConstructor
public class RefreshToken {

	@Id
	private String token;

	private String accessToken;

	private Long memberId;
	private String userAgent;

	@TimeToLive
	private Long expireSec;

	public void checkTokenOwnership(Long reqMemberId, String reqAccessToken) {

		if (!this.memberId.equals(reqMemberId)) {
			throw new RestApiException(FailResponseStatus.INVALID_RTK_TOKEN_MEMBER_NOT_MATCHED);
		} else if (!this.accessToken.equals(reqAccessToken)) {
			throw new RestApiException(FailResponseStatus.INVALID_RTK_TOKEN_ATK_NOT_MATCHED);
		}

	}

	public LocalDateTime calculateExpireAt() {
		return LocalDateTime.now().plusSeconds(this.expireSec);
	}

}
