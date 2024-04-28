package com.LetMeDoWith.LetMeDoWith.util;

import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@UtilityClass
@Slf4j
public class AuthUtil {
    
    private final String AUTHORIZATION_KEY = "AUTHORIZATION";
    
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
    
}