package com.LetMeDoWith.LetMeDoWith.common;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ApiResponseSuccessStatus {
    OK("OK", "OK", HttpStatus.OK, "성공적으로 조회하였습니다."),
    NOT_FOUND("NOT_FOUND", "NOT_FOUND", HttpStatus.NOT_FOUND, "존재하지 않는 API Endpoint입니다.");


    private String statusName;
    private String code;
    private HttpStatus httpStatus;
    private String message;

    @Override
    public String toString() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
