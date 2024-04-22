package com.LetMeDoWith.LetMeDoWith.provider;

import com.LetMeDoWith.LetMeDoWith.dto.valueObject.AccessTokenVO;
import com.LetMeDoWith.LetMeDoWith.entity.auth.RefreshToken;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.repository.auth.RefreshTokenRedisRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthTokenProvider {
    
    private final SecretKey secretKey;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final String KID = "kid";
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
    public AccessTokenVO createAccessToken(Long memberId) {
        
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
        
        return new AccessTokenVO(accessToken,
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
     * Unsigned token에서 kid를 가져온다,
     *
     * @param token Unsigned ID token
     * @param aud   자격증명 제공자에서 발급한 어플리케이션 아이디
     * @param iss   자걱증명 제공자의 url
     * @return ID token의 kid 값
     */
    public String getKidFromUnsignedTokenHeader(String token, String aud, String iss) {
        return (String) getUnsignedTokenClaims(token, aud, iss).getHeader().get(KID);
    }
    
    /**
     * Encoding된 Signed-token 에서 signature를 분리한다.
     *
     * @param signedToken signature를 포함하는 token 전문
     * @return signature가 제거된 token
     */
    
    private String getUnsignedToken(String signedToken) {
        String[] splitToken = signedToken.split("\\.");
        
        if (splitToken.length != 3) {
            throw new IllegalArgumentException("올바르지 않은 Token입니다!");
        }
        
        return splitToken[0] + '.' + splitToken[1] + '.';
    }
    
    /**
     * OIDC ID Token의 iss, aud, 만료일자를 검증하고, parse한다.
     *
     * @param token ID Token. Signature가 제거되있어야 한다.
     * @param aud   자격증명 제공자에서 발급한 어플리케이션 아이디
     * @param iss   자걱증명 제공자의 url
     * @return parsing된 ID Token.
     */
    private Jwt<Header, Claims> getUnsignedTokenClaims(String token, String aud, String iss) {
        try {
            return Jwts.parserBuilder()
                       .requireAudience(aud)  // aud(provider에 지정한 어플리케이션 아이디) 가 같은지 확인
                       .requireIssuer(iss) // iss(issuer)가 알맞은 provider인지 확인
                       .build()
                       .parseClaimsJwt(getUnsignedToken(token));
        } catch (ExpiredJwtException e) { // 파싱하면서 만료된 토큰인지 확인.
            throw new RestApiException(FailResponseStatus.TOKEN_EXPIRED, e);
        } catch (SignatureException e) {
            throw new RestApiException(FailResponseStatus.INVALID_TOKEN, e);
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
    
    public enum TokenType {
        ATK, RTK
    }
    
}