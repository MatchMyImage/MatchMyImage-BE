package com.LetMeDoWith.LetMeDoWith.presentation.auth.dto;

import com.LetMeDoWith.LetMeDoWith.domain.auth.enums.SocialProvider;

public record CreateAccessTokenReqDto(SocialProvider provider, String idToken) {

}