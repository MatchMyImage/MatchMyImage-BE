package com.LetMeDoWith.LetMeDoWith.provider;

import com.LetMeDoWith.LetMeDoWith.dto.valueObject.AuthTokenVO;
import com.LetMeDoWith.LetMeDoWith.entity.auth.RefreshToken;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.repository.auth.RefreshTokenRedisRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthTokenProvider {
    
    private final SecretKey secretKey;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    @Value("${auth.jwt.atk-duration-min}")
    private Long atkDurationMin;
    @Value("${auth.jwt.rtk-duration-day}")
    private Long rtkDurationDay;
    @Value("${auth.jwt.issuer}")
    private String issuer;
    
    // private final long refreshExpireTime = 1 * 60 * 1000L * 60 * 24 * 14; // 14일
    
    @Autowired
    public AuthTokenProvider(@Value("${auth.jwt.secret}") String secret,
                             RefreshTokenRedisRepository refreshTokenRedisRepository) {
        
        // plain secret Base64로 인코딩
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secret.getBytes());
        
        // SecretKey 객체 생성
        this.secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
        
        // Repository 초기화
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
    }
    
    /**
     * 서버 Access Token 생성
     *
     * @param memberId
     * @return
     */
    public AuthTokenVO createAccessToken(Long memberId) {
        
        Date nowDate = new Date();
        
        long accessExpireTime = atkDurationMin * 60 * 1000L;
        Date expireAt = new Date(nowDate.getTime() + accessExpireTime);
        
        String accessToken = Jwts.builder()
                                 .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                                 .setIssuer(this.issuer)
                                 .setIssuedAt(nowDate)
                                 .setExpiration(expireAt)
                                 .setSubject(TokenType.ATK.name())
                                 .claim("memberId", memberId)
                                 .signWith(secretKey)
                                 .compact();
        
        return new AuthTokenVO(accessToken,
                               LocalDateTime.ofInstant(expireAt.toInstant(),
                                                       ZoneId.systemDefault()));
        
    }
    
    /**
     * 서버 Refresh Token 생성
     *
     * @param memberId
     * @param accessToken
     * @param userAgent
     * @return
     */
    
    public RefreshToken createRefreshToken(Long memberId, String accessToken, String userAgent) {
        
        Date nowDate = new Date();
        Date expireAt = new Date(nowDate.getTime() + rtkDurationDay * 24 * 60 * 60 * 1000L);
        
        String refreshToken = Jwts.builder()
                                  .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                                  .setIssuer(this.issuer)
                                  .setIssuedAt(nowDate)
                                  .setExpiration(expireAt)
                                  .setSubject(TokenType.RTK.name())
                                  .claim("memberId", memberId)
                                  .signWith(secretKey)
                                  .compact();
        
        // redis에 저장
        return refreshTokenRedisRepository.save(RefreshToken.builder()
                                                            .token(refreshToken)
                                                            .accessToken(accessToken)
                                                            .memberId(memberId)
                                                            .userAgent(userAgent)
                                                            .expireSec(
                                                                rtkDurationDay * 24 * 60 * 60)
                                                            .build());
    }
    
    /**
     * Signup token 생성. /token 엔드포인트의 응답으로 memberId를 응답한다.
     * <p>
     * 이후 회원가입 완료 시점에 본 메서드의 JWT를 포함하여 요청하여 회원가입 요청을 인증한다.
     *
     * @param memberId 회원가입을 계속해서 진행할 member의 id.
     * @return
     */
    public AuthTokenVO createSignupToken(Long memberId) {
        Date nowDate = new Date();
        Date expireAt = new Date(nowDate.getTime() + TokenType.SIGNUP.expireTime);
        
        String signupToken = Jwts.builder()
                                 .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                                 .setIssuer(this.issuer)
                                 .setIssuedAt(nowDate)
                                 .setExpiration(expireAt)
                                 .setSubject(TokenType.SIGNUP.name())
                                 .claim("memberId", memberId)
                                 .signWith(secretKey)
                                 .compact();
        
        return new AuthTokenVO(signupToken,
                               LocalDateTime.ofInstant(expireAt.toInstant(),
                                                       ZoneId.systemDefault()));
    }
    
    
    /**
     * Token payload 추출
     *
     * @param token
     * @return
     */
    
    public Claims getTokenPayload(final String token) {
        return parseTokenToJws(token, secretKey).getBody();
    }
    
    /**
     * Token(ATK, RTK) 검증 및 memberId 추출
     *
     * @param token
     * @param tokenType
     * @return
     */
    public Long validateToken(final String token, final TokenType tokenType) {
        final Jws<Claims> claims = parseTokenToJws(token, secretKey);
        
        if (claims.getBody().get("sub").equals(tokenType.name()) && claims.getBody()
                                                                          .get("iss")
                                                                          .equals(this.issuer)) {
            return Long.parseLong(claims.getBody().get("memberId").toString());
        } else {
            throw new RestApiException(FailResponseStatus.INVALID_TOKEN);
        }
        
    }
    
    /**
     * String 형태의 JWT를 서명을 포함한 JWS 형태로 parsing 한다. parsing 과정에서 서명을 검증한다.
     *
     * @param token String 형태의 난독화된 JWT
     * @param key   JWT를 서명한 키
     * @return pasring된 토큰
     */
    
    public Jws<Claims> parseTokenToJws(final String token, final Key key) {
        
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(key)
                       .build()
                       .parseClaimsJws(token);
        } catch (SignatureException e) {
            throw new RestApiException(FailResponseStatus.INVALID_TOKEN, e);
        } catch (ExpiredJwtException e) {
            throw new RestApiException(FailResponseStatus.TOKEN_EXPIRED, e);
        } catch (Exception e) {
            log.error(e.toString());
            throw new RestApiException(FailResponseStatus.INVALID_TOKEN, e);
        }
    }
    
    
    /**
     * n, e로 public key를 계산한다.
     *
     * @param modulus
     * @param exponent
     * @return 계산된 public key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public Key getRSAPublicKey(String modulus, String exponent)
        throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        
        byte[] decodeN = Base64.getUrlDecoder().decode(modulus);
        byte[] decodeE = Base64.getUrlDecoder().decode(exponent);
        
        BigInteger n = new BigInteger(1, decodeN);
        BigInteger e = new BigInteger(1, decodeE);
        
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
        return keyFactory.generatePublic(keySpec);
    }
    
    @AllArgsConstructor
    public enum TokenType {
        ATK(1000 * 60 * 1000L),
        RTK(1000 * 60 * 60 * 24 * 1000L),
        SIGNUP(1000 * 60 * 10L);
        
        final Long expireTime;
    }
    
}