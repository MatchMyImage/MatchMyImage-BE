package com.LetMeDoWith.LetMeDoWith.dto.common;

import lombok.Builder;

@Builder
public record ResponsePageDto<T>(String statusCode, String message, Long page, Integer size, T data) {
}
