package com.LetMeDoWith.LetMeDoWith.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
public record ResponseDto<T>(String statusCode, String message, T data) {

}
