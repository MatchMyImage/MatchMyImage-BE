package com.LetMeDoWith.LetMeDoWith.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum FailResponseStatus {
    
    NOT_FOUND("NOT_FOUND", "E510", HttpStatus.NOT_FOUND, "존재하지 않는 API Endpoint 입니다."),
    
    /**
     * 400 BAD Request Error 클라이언트 요청에 대한 오류
     */
    BAD_REQUEST("BAD_REQUEST", "E200", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_PARAM_ERROR("INVALID_PARAM_ERROR", "E201", HttpStatus.BAD_REQUEST, "요청 파라미터가 잘못되었습니다."),
    
    // Member 도메인 관련 Exception - E21X
    MANDATORY_PARAM_ERROR_NAME("MANDATORY_PARAM_ERROR_NAME",
                               "E211",
                               HttpStatus.BAD_REQUEST,
                               "이름이 잘못되었습니다."),
    MANDATORY_PARAM_ERROR_EMAIL("MANDATORY_PARAM_ERROR_EMAIL",
                                "E210",
                                HttpStatus.BAD_REQUEST,
                                "이메일이 잘못되었습니다."),
    MANDATORY_PARAM_ERROR_PROVIDER("MANDATORY_PARAM_ERROR_PROVIDER",
                                   "E211",
                                   HttpStatus.BAD_REQUEST,
                                   "잘못된 Social Provider 입니다."),
    INVALID_FOLLOWER_MEMBER("INVALID_FOLLOWER_MEMBER",
                            "E212",
                            HttpStatus.BAD_REQUEST,
                            "팔로워 회윈이 유효하지 않습니다."),
    INVALID_FOLLOWING_MEMBER("INVALID_FOLLOW_MEMBER",
                             "E213",
                             HttpStatus.BAD_REQUEST,
                             "팔로잉 가능한 유효 회원이 아닙니다."),
    MEMBER_FOLLOW_NOT_EXIST("MEMBER_FOLLOW_NOT_EXIST",
                            "E214",
                            HttpStatus.BAD_REQUEST,
                            "존재하지 않는 팔로우 이력입니다."),
    LOGIN_ATTEMPTED_REGISTRATION_NOT_COMPLTETE("LOGIN_ATTEMPTED_REGISTRATION_NOT_COMPLTETE",
                                               "E215",
                                               HttpStatus.BAD_REQUEST,
                                               "회원가입을 완료하지 않은 계정으로 로그인하였습니다,"),
    LOGIN_ATTEMPTED_SUSPENED("LOGIN_ATTEMPTED_SUSPENED",
                             "E216",
                             HttpStatus.BAD_REQUEST,
                             "이용이 정지된 회원입니다."),
    LOGIN_ATTEMPTED_WITHDRAWN("LOGIN_ATTEMPTED_WITHDRAWN",
                              "E217",
                              HttpStatus.BAD_REQUEST,
                              "탈퇴한 회원입니다."),
    MEMBER_NOT_EXIST("MEMBER_NOT_EXIST",
                     "E218",
                     HttpStatus.BAD_REQUEST,
                     "존재하지 않는 회원입니다"),
    
    /**
     * 401 UnAuthorized Error 인증 관련 오류
     */
    UNAUTHORIZED("UNAUTHORIZED", "E300", HttpStatus.UNAUTHORIZED, "Request is not authorized."),
    ATK_NOT_EXIST("ATK_NOT_EXIST", "E301", HttpStatus.UNAUTHORIZED, "Access token is not exist."),
    TOKEN_EXPIRED("TOKEN_EXPIRED", "E302", HttpStatus.UNAUTHORIZED, "Token is expired"),
    TOKEN_EXPIRED_BY_ADMIN("TOKEN_EXPIRED_BY_ADMIN",
                           "E303",
                           HttpStatus.UNAUTHORIZED,
                           "Token is expired"),
    INVALID_TOKEN("INVALID_TOKEN", "E304", HttpStatus.UNAUTHORIZED, "Token is not valid"),
    INVALID_RTK_TOKEN_MEMBER_NOT_MATCHED("INVALID_RTK_TOKEN_MEMBER_NOT_MATCHED",
                                         "E305",
                                         HttpStatus.UNAUTHORIZED,
                                         "Token is not valid"),
    INVALID_RTK_TOKEN_ATK_NOT_MATCHED("INVALID_RTK_TOKEN_ATK_NOT_MATCHED",
                                      "E305",
                                      HttpStatus.UNAUTHORIZED,
                                      "Token is not valid"),
    OIDC_ID_TOKEN_PUBKEY_NOT_FOUND("OIDC_ID_TOKEN_PUBKEY_NOT_FOUND",
                                   "E306",
                                   HttpStatus.UNAUTHORIZED,
                                   "OpenID Connect ID Token의 공개키를 찾을 수 없습니다."),
    
    /**
     * 500 Interal Server Error 서버 내부 오류 및 서버 관리자의 조치로 인한 오류
     */
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR",
                          "E400",
                          HttpStatus.INTERNAL_SERVER_ERROR,
                          "내부 서버 에러입니다. 다시 시도하세요.");
    
    private final String statusName;
    private final String statusCode;
    private final HttpStatus httpStatusCode;
    private final String message;
    
    
    @Override
    public String toString() {
        return message;
    }
    
}