package com.LetMeDoWith.LetMeDoWith.util;

import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@UtilityClass
@Slf4j
public class AuthUtil {
    
    private final String AUTHORIZATION_KEY = "AUTHORIZATION";
    private final String KID = "kid";
    
    public String getAccessToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader(AUTHORIZATION_KEY);
        if (authorizationHeader == null) {
            throw new RestApiException(FailResponseStatus.ATK_NOT_EXIST);
        }
        if (!authorizationHeader.startsWith("Bearer")) {
            throw new RestApiException(FailResponseStatus.ATK_NOT_EXIST);
        }
        String accessToken = authorizationHeader.substring(6);
        if (accessToken.isEmpty()) {
            throw new RestApiException(FailResponseStatus.ATK_NOT_EXIST);
        } else {
            return accessToken;
        }
    }
    
    public Long getMemberId() {
        
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return (Long) request.getAttribute("memberId");
        
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
     * ID token의 서명을 검증하고, parsing한다.
     *
     * @param token    ID token
     * @param modulus
     * @param exponent
     * @return parsing된 id token
     */
    public Jws<Claims> verifyOidcToken(String token, String modulus, String exponent) {
        
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(getRSAPublicKey(modulus, exponent))
                       .build()
                       .parseClaimsJws(token);
        } catch (SignatureException e) {
            throw new IllegalArgumentException("잘못된 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("만료된 토큰입니다.");
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Token을 파싱하는 도중 에러가 밣생했습니다.");
        }
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
            throw new IllegalStateException("토큰이 만료되었습니다.");
        } catch (SignatureException e) {
            throw new RuntimeException("잘못된 서명입니다.");
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Token을 파싱하는 도중 에러가 밣생했습니다.");
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
    private Key getRSAPublicKey(String modulus, String exponent)
        throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        
        byte[] decodeN = Base64.getUrlDecoder().decode(modulus);
        byte[] decodeE = Base64.getUrlDecoder().decode(exponent);
        
        BigInteger n = new BigInteger(1, decodeN);
        BigInteger e = new BigInteger(1, decodeE);
        
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
        return keyFactory.generatePublic(keySpec);
    }
    
}