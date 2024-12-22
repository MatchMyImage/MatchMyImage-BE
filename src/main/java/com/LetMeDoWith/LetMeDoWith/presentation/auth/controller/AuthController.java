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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authorization", description = "인증")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final CreateTokenService createTokenService;

    @Operation(summary = "토큰 재발급", description = "새로운 AccessToken과 RefreshToken을 발급 받습니다.")
    @PostMapping("/token/refresh")
    public ResponseEntity createTokenRefresh(@RequestBody CreateTokenRefreshReqDto requestBody) {
        
        String userAgent = HeaderUtil.getUserAgent();
        String accessToken = AuthUtil.getAccessToken();

        CreateRefreshTokenResult result = createTokenService.createRefreshToken(accessToken,
            requestBody.refreshToken(),
            userAgent);

        return ResponseUtil.createSuccessResponse(CreateRefreshTokenResDto.of(result));
    }

    @Operation(summary = "토큰 발급", description = "소셜 로그인 idToken을 통해 AccessToken과 RefreshToken을 발급 받습니다.\nProvider : KAKAO - 카카오 / GOOGLE - 구글 / APPLE - 애플")
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