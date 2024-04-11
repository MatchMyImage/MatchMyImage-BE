package com.LetMeDoWith.LetMeDoWith.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.time.Instant;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class AuthUtilTest {
    
    /*
     * Sample OIDC ID Tokens for test.
     * You cannot utilize this token for any purpose on this system.
     */
    
    public final String SAMPLE_TOKEN_NORMAL = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjllMjQxM2UzODI1YWMyYmFiMTdmZTRkNGJhZDkxMjhjIn0.eyJpc3MiOiJodHRwczovL21vY2suYXV0aGVudGljYXRlLmxldG1lZG93aXRoLmRvIiwiYXVkIjoibGV0bWVkb3dpdGhTYW1wbGVBcHBsaWNhdGlvbiIsInN1YiI6IjViZTg2MzU5MDczYzQzNGJhZDJkYTM5MzIyMjJkYWJlIiwiZXhwIjoxOTk5OTk5OTk5LCJpYXQiOjE3MTI1NjM1MDMsIm5pY2tuYW1lIjoiSm9uaCBEb2UiLCJlbWFpbCI6ImpkMTIzQGxldG1lZG93aXRoLmNvbSJ9.zJMubgT5utETNIaUjuAkoRC74-eyPjVOaiiYIFlzv3eVOrbj7bM7RxpZtFeiYdvmpyiEH7sHbo-qVnTukeLlTaT_wNDeLSS7GYskbC75T_zCGx4e5vt4fgDTI4ov_iVWq9RzawTwFzJqj0b3j9hNST4HkuizQymAt5tnFClEa2fE_ojikecBEhfqdn3PAgOzq-pcHM-7-q1cs0QWrseFeouGvVmsOoiRTyewQ8ouuCf9zaYIf6KUu-lAQPsmsjYmNI2MHlPLCZbnnp3qZo7R-ozX66ZELbZwVZxPvrLqLOjdS4yPk_-aaQhT9s5sB0SljmWh9LLYELyXXkiQbVkx9QU-W0TjgnDpb-ScLQ2C5NXgEAF6OHBGynjyymwi_rvBxfY4l22evluOzBp18ECpc7DOhlcnwrPrS5M4pSCpEd8CLlYTyNvYWBJ7umhhDcub5ZLh6cqZ2LJanDG-UM1nLxRB6QGOFZcazyR1rPeGb7kiXiOh6-E7Wz7zwKvhcAUB";
    public final String SAMPLE_TOKEN_ILLEGAL_SIGNATURE = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjllMjQxM2UzODI1YWMyYmFiMTdmZTRkNGJhZDkxMjhjIn0.eyJpc3MiOiJodHRwczovL21vY2suYXV0aGVudGljYXRlLmxldG1lZG93aXRoLmRvIiwiYXVkIjoibGV0bWVkb3dpdGhTYW1wbGVBcHBsaWNhdGlvbiIsInN1YiI6IjViZTg2MzU5MDczYzQzNGJhZDJkYTM5MzIyMjJkYWJlIiwiZXhwIjoxNzEyNTYzNTAzLCJpYXQiOjE5OTk5OTk5OTksIm5pY2tuYW1lIjoiSm9uaCBEb2UiLCJlbWFpbCI6ImpkMTIzQGxldG1lZG93aXRoLmNvbSJ9.TNM7fBiE7joA5FtMbRB4rrjijg2ctngwSnjFXcjfRByeCos9YewaL9QWiHz0uWJodZB6355ZdEYHaFvoWkGaKe1rbGQ75VRO6b8nqheeNRmed2ZajEZRDUSl5zvKuV49yuDo3sFjwEWHHYJBKpYFbnxJt_rmeWitLhxxfOJomnzdxu5hYY6IAeqLgdt-sfxqNbqziyjMQr6AG3jqvDEasrGSKoiTILnJXpK3paf7I3PRAKVFitaXc8S8ddP0QNrEt2_aqTjJbpSnYiV9OoxgacSJo4c_SKeHKbONis1jjubYAu083T0zH6QZMkfey7rIDmhIIhNpugIpp5-QRtmZ6p_P4-aYZPioLcz-U-rvGlQENwyBTds7-_diafSKRQvHpX7Vzdvin-ZlOeXQDoLchT7wZYNWL6WZv9WcsHZvw1K2NjCvCKb09sPup3YaiTfqze46pxej3lhL5SgXKXQfPEyisdggx2VZgHCnBocGRk60S0OkgBgjX9HZHAYzwMcGAnH";
    public final String SAMPLE_TOKEN_MALFORMED = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjllMjQxM2UzODI1YWMyYmFiMTdmZTRkNGJhZDkxMjhjIn0.eyJpc3MiOiJodHRwczovL21vY2suYXV0aGVudGljYXRlLmxldG1lZG93aXRoLmRvIiwiYXVkIjoibGV0bWVkb3dpdGhTYW1wbGVBcHBsaWNhdGlvbiIsInN1YiI6IjViZTg2MzU5MDczYzQzNGJhZDJkYTM5MzIyMjJkYWJlIiwiZXhwIjoxNzEyNTYzNTAzLCJpYXQiOjE5OTk5OTk5OTksIm5pY2tuYW1lIjoiSm9uaCBEb2UiLCJlbWFpbCI6ImpkMTIzQGxldG1lZG93aXRoLmNvbSJ9.TNM7fBiE7joA5FtMbRB4rrjijg2ctngwSnjFXcjfRByeCos9YewaL9QWiHz0uWJodZB6355ZdEYHaFvoWkGaKe1rbGQ75VRO6b8nqheeNRmed2ZajEZRDUSl5zvKuV49yuDo3sFjwEWHHYJBKpYFbnxJt_rmeWitLhxxfOJomnzdxu5hYY6IAeqLgdt-sfxqNbqziyjMQr6AG3jqvDEasrGSKoiTILnJXpK3paf7I3PRAKVFitaXc8S8ddP0QNrEt2_aqTjJbpSnYiV9OoxgacSJo4c_SKeHKbONis1jjubYAu083T0zH6QZMkfey7rIDmhIIhNpugIpp5-QRtmZ6p_P4-aYZPioLcz-U-rvGlQENwyBTds7-_diafSKRQvHpX7Vzdvin-ZlOeXQDoLchT7wZYNWL6WZv9WcsHZvw1K2NjCvCKb09sPup3YaiTfqze46pxej3lhL5SgXKXQfPEyigx2VZgHCnB.ocGRk60S0OkgBgjX9HZHAYzwMcGAnH";
    public final String SAMPLE_TOKEN_EXPIRED = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjllMjQxM2UzODI1YWMyYmFiMTdmZTRkNGJhZDkxMjhjIn0.eyJpc3MiOiJodHRwczovL21vY2suYXV0aGVudGljYXRlLmxldG1lZG93aXRoLmRvIiwiYXVkIjoibGV0bWVkb3dpdGhTYW1wbGVBcHBsaWNhdGlvbiIsInN1YiI6IjViZTg2MzU5MDczYzQzNGJhZDJkYTM5MzIyMjJkYWJlIiwiZXhwIjoxNzEyNTYzNTAzLCJpYXQiOjE3MTI1NjM1MDMsIm5pY2tuYW1lIjoiSm9uaCBEb2UiLCJlbWFpbCI6ImpkMTIzQGxldG1lZG93aXRoLmNvbSJ9.wnJAIys4Bj5-3JZy-BLKDL_tny7JESfqgO5qBKTtYfMPChMEhF8Biq9njGipZDYLfg71d3EiFqLcyUZdL5yuyesuygRJuYtydtdZbmD1Fsqo1UC0dfUTrCZqfIC0TrfrltJub3OYiFSvpYiz27mWsM-TaWp5x3e9z94LoOtU8EtAwASTF2YJ5e_Erla0ucqMmlD9RuuDc-xUj0SAlH8sIe5OqivCfCKfVrEX4AGYInZzuDfH_GyjgfUo1x_dkHnQIH7pq49-HZtZKHf6dMhIxENTrazOY1Q-P1a4l17T09KD1hVO4iXYNCtM5LcoksPo5wXE0GbcwmE_2Tg3N6MniyVXJBsdh4KyyPovykdNiVxu2qy73iGqwZRahH35lDz6F879NCkXuBoLoU-LpGHSIlw3IqpE__zp03VO_wJtXRynV-uFNg99boaU1k24lHbFXd_nMI1nu04trokuwCamYxIL_Kz8y3MHoK-VLyaCpHXR90uax_we1niH4hG0h_ms";
    
    public final String SAMPLE_AUD = "letmedowithSampleApplication";
    public final String SAMPLE_KID = "9e2413e3825ac2bab17fe4d4bad9128c";
    public final String SAMPLE_ISS = "https://mock.authenticate.letmedowith.do";
    public final Long SAMPLE_EXPIRE = 1999999999L;
    
    public final String SAMPLE_MOD = "zhMyuF42t7vy2VjnXj2pI2kssakfgaNJqtBqKkh_IBidqKTIM2mEejJ-b0HUwgQ0YzyZGA1OixLxvWuRTrY3j9RXPg0wj7J7e7TkPqZ83sMQ7lUqfzHfR4mMJQ9Si33CFSm8pBkJt38QS9ciLb-uf2cg9N-GSo1e6YAiywlc-w5UOW9Ur_2N5OeHQAWJM1V7LxSbJEakGJG_ivrghrLfh9h-VaYcvfyCJnbkcHGtpubH7LSo5a80_-S9hkvoHuhow27w9mxLm0K4IR1N8BmJbIBc19pMm8i-BQouHL0tbOr0-843GpoidCsXsk-jL9Egqmp9W3qA_WDU6Ra_SFJzFmbC6lqWveUYcKIh7h-qjpkwWrU_88kO5WuX0QiyV4VDj_uRhbtkMxzKWC-QVFGOhG5h2FJnC1lL1lQaIPa5KfxcxpptThLho1NKkgQoblItidMb3rxHdxMrWHVMkvgPhbN2Z5Yb3zo0Yxa9Svbh0n73iTB2GNrdM8q8EC12abHZ";
    public final String SAMPLE_EXPONENT = "AQAB";
    
    @Test
    @DisplayName("[SUCCESS] Token signature 추출 테스트")
    void extractSignatureFromNormalTokenTest() {
        String kid = AuthUtil.getKidFromUnsignedTokenHeader(SAMPLE_TOKEN_NORMAL,
                                                            SAMPLE_AUD,
                                                            SAMPLE_ISS);
        
        assertEquals(kid, SAMPLE_KID);
    }
    
    @Test
    @DisplayName("[SUCCESS] ID token 검증 테스트")
    void verifyNormalTokenTest() {
        Jws<Claims> oidcToken = AuthUtil.verifyOidcToken(SAMPLE_TOKEN_NORMAL,
                                                         SAMPLE_MOD,
                                                         SAMPLE_EXPONENT);
        
        assertEquals(oidcToken.getBody().getIssuer(), SAMPLE_ISS);
        assertEquals(oidcToken.getBody().getExpiration(),
                     Date.from(Instant.ofEpochSecond(SAMPLE_EXPIRE)));
    }
    
    @Test
    @DisplayName("[FAIL] 만료된 token에서 signature 추출 시도 ")
    void extractSignatureFromExpiredTokenTest() {
        assertThrows(IllegalStateException.class,
                     () -> AuthUtil.getKidFromUnsignedTokenHeader(SAMPLE_TOKEN_EXPIRED,
                                                                  SAMPLE_AUD,
                                                                  SAMPLE_ISS),
                     "토큰이 만료되었습니다.");
    }
    
    @Test
    @DisplayName("[FAIL] 잘못된 signature 가지는 token에서 signature 추출 시도 ")
    void extractSignatureFromIllegalSignatureTokenTest() {
        assertThrows(RuntimeException.class,
                     () -> AuthUtil.getKidFromUnsignedTokenHeader(SAMPLE_TOKEN_ILLEGAL_SIGNATURE,
                                                                  SAMPLE_AUD,
                                                                  SAMPLE_ISS),
                     "잘못된 서명입니다.");
    }
    
    @Test
    @DisplayName("[FAIL] 잘못된 format 가지는 token에서 signature 추출 시도 ")
    void extractSignatureFromMalformedTokenTest() {
        assertThrows(RuntimeException.class,
                     () -> AuthUtil.getKidFromUnsignedTokenHeader(SAMPLE_TOKEN_MALFORMED,
                                                                  SAMPLE_AUD,
                                                                  SAMPLE_ISS),
                     "Token을 파싱하는 도중 에러가 밣생했습니다.");
    }
    
    @Test
    @DisplayName("[FAIL] 만료된 ID token 검증 시도")
    void verifyExpiredTokenTest() {
        assertThrows(RuntimeException.class,
                     () -> AuthUtil.verifyOidcToken(SAMPLE_TOKEN_EXPIRED,
                                                    SAMPLE_MOD,
                                                    SAMPLE_EXPONENT),
                     "만료된 토큰입니다.");
    }
    
    @Test
    @DisplayName("[FAIL] 잘못된 signature 가지는 ID token 검증 시도")
    void verifyIllegalSignatureTokenTest() {
        assertThrows(IllegalArgumentException.class,
                     () -> AuthUtil.verifyOidcToken(SAMPLE_TOKEN_ILLEGAL_SIGNATURE,
                                                    SAMPLE_MOD,
                                                    SAMPLE_EXPONENT));
    }
    
    @Test
    @DisplayName("[FAIL] 잘못된 format 가지는 ID token 검증 시도")
    void verifyMalformedTokenTest() {
        assertThrows(RuntimeException.class,
                     () -> AuthUtil.verifyOidcToken(SAMPLE_TOKEN_MALFORMED,
                                                    SAMPLE_MOD,
                                                    SAMPLE_EXPONENT));
    }
    
    @Test
    @DisplayName("[FAIL] 잘못된 RSA Key로 ID token 검증 시도")
    void verifyTokenWithInvalidKeyTest() {
        assertThrows(RuntimeException.class,
                     () -> AuthUtil.verifyOidcToken(SAMPLE_TOKEN_NORMAL,
                                                    "SAMPLE_MOD",
                                                    "SAMPLE_EXPONENT"));
    }
}