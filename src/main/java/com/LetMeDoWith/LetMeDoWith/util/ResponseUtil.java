package com.LetMeDoWith.LetMeDoWith.util;

import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.enums.common.SuccessResponseStatus;
import com.LetMeDoWith.LetMeDoWith.dto.common.SuccessResponseDto;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseUtil {

	// SUCCESS
	public static <T> ResponseEntity<SuccessResponseDto<T>> createSuccessResponse() {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(true)
			.statusCode(SuccessResponseStatus.OK.getStatusCode())
			.message(SuccessResponseStatus.OK.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, SuccessResponseStatus.OK.getHttpStatusCode());
	}

	public static <T> ResponseEntity<SuccessResponseDto<T>> createSuccessResponse(HttpHeaders httpHeaders) {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(true)
			.statusCode(SuccessResponseStatus.OK.getStatusCode())
			.message(SuccessResponseStatus.OK.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, SuccessResponseStatus.OK.getHttpStatusCode());
	}

	public static <T> ResponseEntity<SuccessResponseDto<T>> createSuccessResponse(T data) {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(true)
			.statusCode(SuccessResponseStatus.OK.getStatusCode())
			.message(SuccessResponseStatus.OK.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, SuccessResponseStatus.OK.getHttpStatusCode());
	}

	public static <T> ResponseEntity<SuccessResponseDto<T>> createSuccessResponse(T data, HttpHeaders httpHeaders) {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(true)
			.statusCode(SuccessResponseStatus.OK.getStatusCode())
			.message(SuccessResponseStatus.OK.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, SuccessResponseStatus.OK.getHttpStatusCode());
	}

	public static <T> ResponseEntity<SuccessResponseDto<T>> createSuccessResponse(SuccessResponseStatus status) {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(true)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<SuccessResponseDto<T>> createSuccessResponse(SuccessResponseStatus status, HttpHeaders httpHeaders) {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(true)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<SuccessResponseDto<T>> createSuccessResponse(SuccessResponseStatus status, T data) {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(true)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<SuccessResponseDto<T>> createSuccessResponse(SuccessResponseStatus status, T data, HttpHeaders httpHeaders) {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(true)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, status.getHttpStatusCode());
	}

	/// FAIL
	public static <T> ResponseEntity<SuccessResponseDto<T>> createFailResponse(FailResponseStatus status) {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(false)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<SuccessResponseDto<T>> createFailResponse(FailResponseStatus status, HttpHeaders httpHeaders) {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(false)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<SuccessResponseDto<T>> createFailResponse(FailResponseStatus status, T data) {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(false)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<SuccessResponseDto<T>> createFailResponse(FailResponseStatus status, T data, HttpHeaders httpHeaders) {
		SuccessResponseDto<T> dto = SuccessResponseDto.<T>builder()
			.successYn(false)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, status.getHttpStatusCode());
	}
}
