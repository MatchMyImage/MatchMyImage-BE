package com.LetMeDoWith.LetMeDoWith.application.auth.dto;

import com.LetMeDoWith.LetMeDoWith.domain.auth.model.AccessToken;
import com.LetMeDoWith.LetMeDoWith.domain.auth.model.RefreshToken;
import com.LetMeDoWith.LetMeDoWith.domain.auth.model.SignupToken;

/**
 * 토큰 발급 요청에 대한 응답 데이터 모델
 *
 * @param atk
 * @param rtk
 * @param signupToken
 */
public record Token(
    AuthTokenVO atk,
    AuthTokenVO rtk,
    AuthTokenVO signupToken
) {
    
    /**
     * 일반 토큰 (atk, rtk) 요청 시 응답 포맷
     *
     * @param atk
     * @param rtk
     * @return
     */
//    public static Token atkInit(AuthTokenVO atk, RefreshToken rtk) {
//        return new Token(atk,
//                                     AuthTokenVO.fromRtk(rtk),
//                                     null);
//    }

    public static Token of(AccessToken atk, RefreshToken rtk) {
        return new Token(AuthTokenVO.from(atk), AuthTokenVO.fromRtk(rtk), null);
    }
    
    /**
     * 회원가입 완료 시 사용되는 SIGNUP token 요청시 응답 포맷
     */
    public static Token stkInit(AuthTokenVO signupToken) {
        return new Token(null, null, signupToken);
    }

    public static Token of(SignupToken signUpToken) {
        return new Token(null, null, AuthTokenVO.from(signUpToken));
    }
}