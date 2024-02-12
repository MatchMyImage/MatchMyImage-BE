package com.LetMeDoWith.LetMeDoWith.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum FailResponseStatus {
    BAD_REQUEST("BAD_REQUEST", "E200", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_PARAM_ERROR("INVALID_PARAM_ERROR", "E201", HttpStatus.BAD_REQUEST, "요청 파라미터가 잘못되었습니다."),
    UNAUTHORIZED("UNAUTHORIZED", "E300", HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),
    TOKEN_EXPIRED("TOKEN_EXPIRED", "E310", HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "E400", HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러입니다. 다시 시도하세요."),
    NOT_FOUND("NOT_FOUND", "E500", HttpStatus.NOT_FOUND, "존재하지 않는 API Endpoint 입니다."),

    /**
     * 필수 파라미터 에러시 사용하며, 모든 필수 파라미터에 대해 구현한다.
     */
    MANDATORY_PARAM_ERROR_NAME("MANDATORY_PARAM_ERROR_NAME", "E211", HttpStatus.BAD_REQUEST, "이름이 잘못되었습니다."),
    MANDATORY_PARAM_ERROR_EMAIL("MANDATORY_PARAM_ERROR_EMAIL", "E210", HttpStatus.BAD_REQUEST, "이메일이 잘못되었습니다.");


    private final String statusName;
    private final String statusCode;
    private final HttpStatus httpStatusCode;
    private final String message;


    @Override
    public String toString() {
        return message;
    }

}
