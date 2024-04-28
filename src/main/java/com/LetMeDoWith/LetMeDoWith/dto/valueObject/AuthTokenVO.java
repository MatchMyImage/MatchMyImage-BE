package com.LetMeDoWith.LetMeDoWith.dto.valueObject;

import java.time.LocalDateTime;

public record AuthTokenVO(String token, LocalDateTime expireAt) {

}