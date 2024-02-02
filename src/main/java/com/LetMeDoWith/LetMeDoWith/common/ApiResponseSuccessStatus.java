package com.LetMeDoWith.LetMeDoWith.common;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ApiResponseSuccessStatus {
    OK("OK", "OK", HttpStatus.OK, "성공적으로 조회하였습니다."),
    OBJECT_NOT_FOUND("OBJECT_NOT_FOUND", "OBJECT_NOT_FOUND", HttpStatus.OK, "조회 결과가 존재하지 않습니다.");


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
