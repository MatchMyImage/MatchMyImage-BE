package com.LetMeDoWith.LetMeDoWith.dto.requestDto;

import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;

public record createAccessTokenReqDto(SocialProvider provider, String idToken) {

}