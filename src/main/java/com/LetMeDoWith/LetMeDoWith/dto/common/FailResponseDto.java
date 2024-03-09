package com.LetMeDoWith.LetMeDoWith.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FailResponseDto {

	private final String statusCode;
	private final String message;

}
