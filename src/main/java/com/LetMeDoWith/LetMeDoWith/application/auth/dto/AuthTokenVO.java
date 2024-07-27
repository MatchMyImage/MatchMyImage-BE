package com.LetMeDoWith.LetMeDoWith.application.auth.dto;

import java.time.LocalDateTime;

public record AuthTokenVO(String token, LocalDateTime expireAt) {

}