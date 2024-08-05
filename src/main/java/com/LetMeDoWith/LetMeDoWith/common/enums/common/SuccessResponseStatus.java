package com.LetMeDoWith.LetMeDoWith.common.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum SuccessResponseStatus {
    OK("OK", "S100", HttpStatus.OK, "요청에 성공했습니다."),

    
    // 반환값이 없는 요청을 성공했을 때 얻는 메세지
    OK_WITHOUT_DATA("OBJECT_NOT_FOUND", "S101", HttpStatus.NO_CONTENT, "성공적으로 요청되었습니다."), // TODO - 삭제 예정
    LOGIN_SUCCESS("LOGIN_SUCCESS", "S102", HttpStatus.OK, "로그인에 성공하였습니다."), // TODO - 삭제 협의 대상


    PROCEED_TO_SIGNUP("PROCEED_TO_SIGNUP",
                      "S301",
                      HttpStatus.OK,
                      "가입되지 않은 사용자입니다. 회원가입을 진행하세요."); // TODO - 301을 주는 것이 맞아 보임
    
    
    private final String statusName;
    private final String statusCode;
    private final HttpStatus httpStatusCode;
    private final String message;
    
    @Override
    public String toString() {
        return message;
    }
    
}