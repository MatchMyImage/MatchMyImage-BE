package com.LetMeDoWith.LetMeDoWith.util;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.LetMeDoWith.LetMeDoWith.entity.auth.RefreshToken;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.repository.auth.RefreshTokenRedisRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@Component
public class AuthTokenProvider {

	private final SecretKey secretKey;

	@Value("${auth.jwt.atk-duration-min}")
	private Long atkDurationMin;

	@Value("${auth.jwt.rtk-duration-day}")
	private Long rtkDurationDay;

	@Value("${auth.jwt.issuer}")
	private String issuer;

	private final RefreshTokenRedisRepository refreshTokenRedisRepository;

	// private final long refreshExpireTime = 1 * 60 * 1000L * 60 * 24 * 14; // 14일

	@Autowired
	public AuthTokenProvider(@Value("${auth.jwt.secret}") String secret, RefreshTokenRedisRepository refreshTokenRedisRepository) {

		// plain secret Base64로 인코딩
		String keyBase64Encoded = Base64.getEncoder().encodeToString(secret.getBytes());

		// SecretKey 객체 생성
		this.secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());

		// Repository 초기화
		this.refreshTokenRedisRepository = refreshTokenRedisRepository;
	}

	/**
	 * 서버 Access Token 생성
	 * @param memberId
	 * @return
	 */
	public Map<Object, Object> createAccessToken(String memberId) {

		Date nowDate = new Date();

		long accessExpireTime = atkDurationMin * 60 * 1000L;
		Date expireAt = new Date(nowDate.getTime() + accessExpireTime);

		String accessToken = Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setIssuer(this.issuer)
			.setIssuedAt(nowDate)
			.setExpiration(expireAt)
			.setSubject("ATK")
			.claim("memberId", memberId)
			.signWith(secretKey)
			.compact();

		HashMap<Object, Object> result = new HashMap<>();
		result.put("token", accessToken);
		result.put("expireAt", expireAt);

		return result;
	}

	/**
	 * 서버 Refresh Token 생성
	 * @param memberId
	 * @return
	 */
	public RefreshToken createRefreshToken(Long memberId, String accessToken, String userAgent) {
		String refreshToken = UUID.randomUUID().toString();

		return RefreshToken.builder()
			.token(refreshToken)
			.accessToken(accessToken)
			.memberId(memberId)
			.userAgent(userAgent)
			.expireSec(rtkDurationDay * 24 * 60 * 60)
			.build();
	}

	/**
	 * 서버 Refresh Token 생성
	 * @param memberId
	 * @param ttl
	 * @return
	 */
	public RefreshToken createRefreshToken(Long memberId, String accessToken, String userAgent, Long ttl) {

		Date nowDate = new Date();
		Date expireAt = new Date(nowDate.getTime() + rtkDurationDay * 24 * 60 * 60 * 1000L);

		String refreshToken = Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setIssuer(this.issuer)
			.setIssuedAt(nowDate)
			.setExpiration(expireAt)
			.setSubject("ATK")
			.claim("memberId", memberId)
			.signWith(secretKey)
			.compact();

		return RefreshToken.builder()
			.token(refreshToken)
			.accessToken(accessToken)
			.memberId(memberId)
			.userAgent(userAgent)
			.expireSec(rtkDurationDay * 24 * 60 * 60)
			.build();
	}

	/**
	 * Token payload 추출
	 * @param token
	 * @return
	 */
	public Claims getTokenPayload(final String token) {
		return parseTokenToJws(token, secretKey, false).getBody();
	}

	/**
	 * Access Token 검증 및 id 추출
	 * @param token
	 * @return
	 */
	public String validateAccessToken(final String token, final Boolean refresh) {
		try {
			final Jws<Claims> claims = parseTokenToJws(token, secretKey, refresh);

			if (claims.getBody().get("sub").equals("ATK") && claims.getBody().get("iss").equals(this.issuer)) {
				return claims.getBody().get("memberId").toString();

			} else {
				throw new RestApiException(FailResponseStatus.INVALID_TOKEN);
			}
		} catch (final RestApiException e) {
			throw e;
		}
		catch (final JwtException e) {
			throw new RestApiException(FailResponseStatus.INVALID_TOKEN);
		}
	}

	private Jws<Claims> parseTokenToJws(final String token, final SecretKey secretKey, final Boolean refresh) {
		try {

			return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);

		} catch (ExpiredJwtException e) {
			// JWT 유효시간 초과
			if(refresh.equals(Boolean.TRUE)) {
				// refresh 대상인 ATK인 경우
				return Jwts.parserBuilder()
					.build()
					.parseClaimsJws(token);
			} else {
				// 인증/인가 대상인 ATK인 경우
				throw new RestApiException(FailResponseStatus.TOKEN_EXPIRED);
			}

		} catch (SignatureException | MalformedJwtException e) {
			// JWT 시그니터 검증 실패
			e.printStackTrace();
			throw new RestApiException(FailResponseStatus.INVALID_TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestApiException(FailResponseStatus.INVALID_TOKEN);
		}
	}


}
