package com.LetMeDoWith.LetMeDoWith.dto.valueObject;

import com.LetMeDoWith.LetMeDoWith.enums.member.Gender;
import java.time.LocalDate;
import lombok.Builder;


@Builder
public record MemberPersonalInfoVO(
    String nickname,
    String selfDescription,
    Gender gender,
    LocalDate dateOfBirth,
    String profileImageUrl) {
    
}