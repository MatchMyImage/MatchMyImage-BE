package com.LetMeDoWith.LetMeDoWith.Common;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ApiResponseStatus {
    OK("OK", "OK", HttpStatus.OK, "성공적으로 조회하였습니다.", true),
    BAD_REQUEST("BAD_REQUEST", "BAD_REQUEST", HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", false),
    NOT_FOUND("NOT_FOUND", "NOT_FOUND", HttpStatus.NOT_FOUND, "존재하지 않는 API Endpoint입니다.", false),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러입니다. 다시 시도하세요.", false),
    OBJECT_NOT_FOUND("OBJECT_NOT_FOUND", "OBJECT_NOT_FOUND", HttpStatus.OK, "조회 결과가 존재하지 않습니다.", true),
    INVALID_PARAM_ERROR("INVALID_PARAM_ERROR", "INVALID_PARAM_ERROR", HttpStatus.BAD_REQUEST, "요청 파라미터가 잘못되었습니다.", false),
    TOKEN_EXPRIED("TOKEN_EXPRIED", "TOKEN_EXPRIED", HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.", false),
    UNAUTHORIZED("UNAUTHORIZED", "UNAHTHORIZED", HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다.", false),

    /**
     * 필수 파라미터 에러시 사용하며, 모든 필수 파라미터에 대해 구현한다.
     */
    MANDATORY_PARAM_ERROR_NAME("MANDATORY_PARAM_ERROR_NAME", "MANDATORY_PARAM_ERROR", HttpStatus.BAD_REQUEST, "이름이 잘못되었습니다.", false),
    MANDATORY_PARAM_ERROR_EMAIL("MANDATORY_PARAM_ERROR_EMAIL", "MANDATORY_PARAM_ERROR", HttpStatus.BAD_REQUEST, "이메일이 잘못되었습니다.", false);


    private String attribute;
    private String code;
    private HttpStatus httpStatus;
    private String message;

    // 성공 응답인지, 실패 응답인지 구분 필드
    private boolean successResponseYn;

    @Override
    public String toString() {
        return message;
    }

    public boolean isFailResponse() {
        return !successResponseYn;
    }
}
