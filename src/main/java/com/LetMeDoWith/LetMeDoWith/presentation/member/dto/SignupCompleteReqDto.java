package com.LetMeDoWith.LetMeDoWith.presentation.member.dto;

import com.LetMeDoWith.LetMeDoWith.common.enums.member.Gender;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record SignupCompleteReqDto(
    String nickname,
    LocalDate dateOfBirth,
    Gender gender,
    CreateMemberTermAgreeReqDto agreements) {
    
}