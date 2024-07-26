package com.LetMeDoWith.LetMeDoWith.dto.requestDto;

import com.LetMeDoWith.LetMeDoWith.enums.member.Gender;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record SignupCompleteReq(
    String nickname,
    LocalDate dateOfBirth,
    Gender gender,
    CreateMemberTermAgreeReq agreements) {
    
}