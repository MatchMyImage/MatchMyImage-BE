package com.LetMeDoWith.LetMeDoWith.presentation.auth.dto;

import com.LetMeDoWith.LetMeDoWith.application.auth.dto.AuthTokenVO;
import com.LetMeDoWith.LetMeDoWith.application.auth.dto.CreateTokenResult;
import lombok.Builder;

@Builder
public record CreateTokenResDto(AuthTokenVO atk, AuthTokenVO rtk, AuthTokenVO signupToken) {
    
    public static CreateTokenResDto fromCreateTokenResult(CreateTokenResult result) {
        return CreateTokenResDto.builder()
                                .atk(result.atk())
                                .rtk(result.rtk())
                                .signupToken(result.signupToken())
                                .build();
    }
}