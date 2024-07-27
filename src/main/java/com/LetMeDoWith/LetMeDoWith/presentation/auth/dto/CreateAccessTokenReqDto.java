package com.LetMeDoWith.LetMeDoWith.presentation.auth.dto;

import com.LetMeDoWith.LetMeDoWith.common.enums.SocialProvider;

public record CreateAccessTokenReqDto(SocialProvider provider, String idToken) {

}