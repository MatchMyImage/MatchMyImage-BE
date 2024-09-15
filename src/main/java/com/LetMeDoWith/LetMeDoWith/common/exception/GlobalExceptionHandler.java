package com.LetMeDoWith.LetMeDoWith.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.LetMeDoWith.LetMeDoWith.common.dto.FailResponseDto;
import com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus;

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

	@ExceptionHandler({RestApiAuthException.class})
	protected ResponseEntity<FailResponseDto> handleRestApiAuthException(RestApiAuthException ex) {
		ex.printStackTrace();
		FailResponseDto responseBody = FailResponseDto.builder()
				.statusCode(ex.getStatus().getStatusCode()) // TODO - Auth 관련 Error Code Enum 따로 관리하고, 해당 부분은 항상 같은값을 Response / 내부에서만 ErrorCode 로깅 외부에는 인증 오류 원인 숨기기
				.message(ex.getStatus().getMessage())
				.build();
		return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
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
