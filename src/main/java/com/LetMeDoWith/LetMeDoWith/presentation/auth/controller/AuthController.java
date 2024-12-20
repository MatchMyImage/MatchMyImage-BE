package com.LetMeDoWith.LetMeDoWith.presentation.auth.controller;

import com.LetMeDoWith.LetMeDoWith.application.auth.dto.CreateRefreshTokenResult;
import com.LetMeDoWith.LetMeDoWith.common.util.AuthUtil;
import com.LetMeDoWith.LetMeDoWith.application.auth.dto.CreateTokenResult;
import com.LetMeDoWith.LetMeDoWith.application.auth.service.CreateTokenService;
import com.LetMeDoWith.LetMeDoWith.common.exception.status.SuccessResponseStatus;
import com.LetMeDoWith.LetMeDoWith.common.util.HeaderUtil;
import com.LetMeDoWith.LetMeDoWith.common.util.ResponseUtil;
import com.LetMeDoWith.LetMeDoWith.presentation.auth.dto.CreateAccessTokenReqDto;
import com.LetMeDoWith.LetMeDoWith.presentation.auth.dto.CreateTokenRefreshReqDto;
import com.LetMeDoWith.LetMeDoWith.presentation.auth.dto.CreateRefreshTokenResDto;
import com.LetMeDoWith.LetMeDoWith.presentation.auth.dto.CreateTokenResDto;
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
    
    private final CreateTokenService createTokenService;
    
    @PostMapping("/token/refresh")
    public ResponseEntity createTokenRefresh(@RequestBody CreateTokenRefreshReqDto requestBody) {
        
        String userAgent = HeaderUtil.getUserAgent();
        String accessToken = AuthUtil.getAccessToken();

        CreateRefreshTokenResult result = createTokenService.createRefreshToken(accessToken,
            requestBody.refreshToken(),
            userAgent);

        return ResponseUtil.createSuccessResponse(CreateRefreshTokenResDto.of(result));
    }
    
    @PostMapping("/token")
    public ResponseEntity createToken(
        @RequestBody CreateAccessTokenReqDto createAccessTokenReqDto) {
        
        CreateTokenResult createTokenResultRequestResult = createTokenService.createToken(
            createAccessTokenReqDto.provider(),
            createAccessTokenReqDto.idToken()
        );
        
        return ResponseUtil.createSuccessResponse(
            createTokenResultRequestResult.signupToken() == null
                ? SuccessResponseStatus.OK
                : SuccessResponseStatus.PROCEED_TO_SIGNUP
            , CreateTokenResDto.fromCreateTokenResult(createTokenResultRequestResult));
    }
}