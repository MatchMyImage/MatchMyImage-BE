package com.LetMeDoWith.LetMeDoWith.common.interceptor;

import com.LetMeDoWith.LetMeDoWith.application.auth.provider.AccessTokenProvider;
import com.LetMeDoWith.LetMeDoWith.application.auth.provider.SignupTokenProvider;
import com.LetMeDoWith.LetMeDoWith.common.util.AuthUtil;
import com.LetMeDoWith.LetMeDoWith.domain.auth.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticateInterceptor implements HandlerInterceptor {
    
    private final AccessTokenProvider accessTokenProvider;
    private final SignupTokenProvider signupTokenProvider;
    
    private final String SIGNUP_COMPLETE_API_URI = "/api/v1/member";
    private final String SIGNUP_COMPLETE_API_METHOD = "PUT";
    
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)
        throws Exception {
        
        String uri = request.getRequestURI();
        String method = request.getMethod();
        
        // Do not block preflight
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        
        // 회원가입 완료 API 요청은 SIGNUP token으로 인증한다.
        boolean isSignupCompleteReq =
            uri.equals(SIGNUP_COMPLETE_API_URI) && method.equals(SIGNUP_COMPLETE_API_METHOD);
        
        String tokenToBeValidated =
            isSignupCompleteReq ? AuthUtil.getSignupToken() : AuthUtil.getAccessToken();
        TokenType tokenType = isSignupCompleteReq ? TokenType.SIGNUP
            : TokenType.ATK;

        Long memberId;
        if(isSignupCompleteReq) {
            memberId = accessTokenProvider.validateAccessToken(tokenToBeValidated);
        }else {
            memberId = signupTokenProvider.validateSignupToken(tokenToBeValidated);
        }

        request.setAttribute("memberId", memberId);
        
        return true;
        
    }
}