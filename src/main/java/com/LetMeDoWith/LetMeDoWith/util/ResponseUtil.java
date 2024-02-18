package com.LetMeDoWith.LetMeDoWith.util;

import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.enums.common.SuccessResponseStatus;
import com.LetMeDoWith.LetMeDoWith.dto.ResponseDto;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseUtil {

	// SUCCESS
	public static <T> ResponseEntity<ResponseDto<T>> createSuccessResponse() {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(true)
			.statusCode(SuccessResponseStatus.OK.getStatusCode())
			.message(SuccessResponseStatus.OK.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, SuccessResponseStatus.OK.getHttpStatusCode());
	}

	public static <T> ResponseEntity<ResponseDto<T>> createSuccessResponse(HttpHeaders httpHeaders) {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(true)
			.statusCode(SuccessResponseStatus.OK.getStatusCode())
			.message(SuccessResponseStatus.OK.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, SuccessResponseStatus.OK.getHttpStatusCode());
	}

	public static <T> ResponseEntity<ResponseDto<T>> createSuccessResponse(T data) {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(true)
			.statusCode(SuccessResponseStatus.OK.getStatusCode())
			.message(SuccessResponseStatus.OK.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, SuccessResponseStatus.OK.getHttpStatusCode());
	}

	public static <T> ResponseEntity<ResponseDto<T>> createSuccessResponse(T data, HttpHeaders httpHeaders) {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(true)
			.statusCode(SuccessResponseStatus.OK.getStatusCode())
			.message(SuccessResponseStatus.OK.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, SuccessResponseStatus.OK.getHttpStatusCode());
	}

	public static <T> ResponseEntity<ResponseDto<T>> createSuccessResponse(SuccessResponseStatus status) {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(true)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<ResponseDto<T>> createSuccessResponse(SuccessResponseStatus status, HttpHeaders httpHeaders) {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(true)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<ResponseDto<T>> createSuccessResponse(SuccessResponseStatus status, T data) {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(true)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<ResponseDto<T>> createSuccessResponse(SuccessResponseStatus status, T data, HttpHeaders httpHeaders) {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(true)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, status.getHttpStatusCode());
	}

	/// FAIL
	public static <T> ResponseEntity<ResponseDto<T>> createFailResponse(FailResponseStatus status) {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(false)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<ResponseDto<T>> createFailResponse(FailResponseStatus status, HttpHeaders httpHeaders) {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(false)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(null)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<ResponseDto<T>> createFailResponse(FailResponseStatus status, T data) {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(false)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, status.getHttpStatusCode());
	}

	public static <T> ResponseEntity<ResponseDto<T>> createFailResponse(FailResponseStatus status, T data, HttpHeaders httpHeaders) {
		ResponseDto<T> dto = ResponseDto.<T>builder()
			.successYn(false)
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.data(data)
			.build();
		return new ResponseEntity<>(dto, httpHeaders, status.getHttpStatusCode());
	}
}
