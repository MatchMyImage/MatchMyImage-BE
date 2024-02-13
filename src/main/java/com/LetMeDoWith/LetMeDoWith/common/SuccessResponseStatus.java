package com.LetMeDoWith.LetMeDoWith.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum SuccessResponseStatus {
    OK("OK", "S100", HttpStatus.OK, "성공적으로 조회하였습니다."),
    OBJECT_NOT_FOUND("OBJECT_NOT_FOUND", "S101", HttpStatus.OK, "조회 결과가 존재하지 않습니다.");


    private final String statusName;
    private final String statusCode;
    private final HttpStatus httpStatusCode;
    private final String message;

    @Override
    public String toString() {
        return message;
    }

}
