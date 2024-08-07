package com.LetMeDoWith.LetMeDoWith.presentation.auth.controller;

import com.LetMeDoWith.LetMeDoWith.common.util.AuthUtil;
import com.LetMeDoWith.LetMeDoWith.presentation.auth.dto.CreateAccessTokenReqDto;
import com.LetMeDoWith.LetMeDoWith.presentation.auth.dto.CreateTokenRefreshReqDto;
import com.LetMeDoWith.LetMeDoWith.presentation.auth.dto.CreateTokenRefreshResDto;
import com.LetMeDoWith.LetMeDoWith.presentation.auth.dto.CreateTokenResDto;
import com.LetMeDoWith.LetMeDoWith.common.enums.common.SuccessResponseStatus;
import com.LetMeDoWith.LetMeDoWith.application.auth.service.AuthService;
import com.LetMeDoWith.LetMeDoWith.common.util.HeaderUtil;
import com.LetMeDoWith.LetMeDoWith.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/token/refresh")
    public ResponseEntity createTokenRefresh(@RequestBody CreateTokenRefreshReqDto requestBody) {
        
        String userAgent = HeaderUtil.getUserAgent();
        String accessToken = AuthUtil.getAccessToken();

        CreateTokenRefreshResDto result = authService.createTokenRefresh(accessToken,
                                                                         requestBody.refreshToken(),
                                                                         userAgent);
        
        return ResponseUtil.createSuccessResponse(result);
    }
    
    @PostMapping("/token")
    public ResponseEntity createToken(
        @RequestBody CreateAccessTokenReqDto createAccessTokenReqDto) {
        
        CreateTokenResDto tokenRequestResult = authService.createToken(
            createAccessTokenReqDto.provider(),
            createAccessTokenReqDto.idToken()
        );
        
        return ResponseUtil.createSuccessResponse(
            tokenRequestResult.signupToken() == null
                ? SuccessResponseStatus.OK
                : SuccessResponseStatus.PROCEED_TO_SIGNUP
            , tokenRequestResult);
    }
}