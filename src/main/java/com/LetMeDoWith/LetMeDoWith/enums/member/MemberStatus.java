package com.LetMeDoWith.LetMeDoWith.enums.member;

import com.LetMeDoWith.LetMeDoWith.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus implements BaseEnum {
    
    NORMAL("NORMAL", "일반"),
    SUSPENDED("SUSPENDED", "일시정지"),
    WITHDRAWN("WITHDRAWN", "탈퇴"),
    // 소셜 인증 이후 회원가입 완료 이전의 상태.
    SOCIAL_AUTHENTICATED("SOCIAL_AUTHENTICATED", "소셜 인증됨");
    
    
    private final String code;
    private final String description;
}