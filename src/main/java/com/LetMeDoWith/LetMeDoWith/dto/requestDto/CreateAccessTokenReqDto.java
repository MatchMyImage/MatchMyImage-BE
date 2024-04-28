package com.LetMeDoWith.LetMeDoWith.dto.requestDto;

import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;

public record CreateAccessTokenReqDto(SocialProvider provider, String idToken) {

}