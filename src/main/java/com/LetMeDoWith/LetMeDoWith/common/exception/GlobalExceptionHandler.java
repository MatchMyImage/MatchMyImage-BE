package com.LetMeDoWith.LetMeDoWith.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.LetMeDoWith.LetMeDoWith.common.dto.FailResponseDto;
import com.LetMeDoWith.LetMeDoWith.common.enums.common.FailResponseStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({RestApiException.class})
	protected ResponseEntity<FailResponseDto> handleRestApiException(RestApiException ex) {
		ex.printStackTrace();
		FailResponseDto responseBody = FailResponseDto.builder()
			.statusCode(ex.getStatus().getStatusCode())
			.message(ex.getStatus().getMessage())
			.build();
		return new ResponseEntity<>(responseBody, ex.getStatus().getHttpStatusCode());
	}

	@ExceptionHandler({Exception.class})
	protected ResponseEntity<FailResponseDto> handleException(Exception ex) {
		ex.printStackTrace();
		FailResponseDto responseBody = FailResponseDto.builder()
			.statusCode(FailResponseStatus.INTERNAL_SERVER_ERROR.getStatusCode())
			.message(ex.getMessage())
			.build();
		return new ResponseEntity<>(responseBody, FailResponseStatus.INTERNAL_SERVER_ERROR.getHttpStatusCode());
	}

}
