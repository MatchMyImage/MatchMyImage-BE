package com.LetMeDoWith.LetMeDoWith.dto.common;


import lombok.Builder;


@Builder
public record FailResponseDto(String statusCode, String message) {

}
