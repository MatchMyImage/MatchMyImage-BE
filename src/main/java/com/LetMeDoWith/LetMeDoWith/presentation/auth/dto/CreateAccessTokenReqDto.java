package com.LetMeDoWith.LetMeDoWith.presentation.auth.dto;

import com.LetMeDoWith.LetMeDoWith.domain.auth.enums.SocialProvider;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateAccessTokenReqDto(SocialProvider provider, String idToken) {

}