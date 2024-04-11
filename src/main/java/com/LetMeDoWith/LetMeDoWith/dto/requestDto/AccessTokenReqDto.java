package com.LetMeDoWith.LetMeDoWith.dto.requestDto;

import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;

public record AccessTokenReqDto(SocialProvider provider, String idToken) {

}