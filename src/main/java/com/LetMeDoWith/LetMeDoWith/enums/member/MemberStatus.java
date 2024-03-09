package com.LetMeDoWith.LetMeDoWith.enums.member;

import com.LetMeDoWith.LetMeDoWith.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus implements BaseEnum {
    
    NORMAL("NORMAL", "일반"),
    SUSPENDED("SUSPENDED", "일시정지"),
    WITHDRAWN("WITHDRAWN", "탈퇴");
    
    
    private final String code;
    private final String description;
}