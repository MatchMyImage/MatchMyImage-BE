package com.LetMeDoWith.LetMeDoWith.dto.requestDto;

import com.LetMeDoWith.LetMeDoWith.enums.member.Gender;
import java.time.LocalDate;

public record SignupCompleteReq(String signupToken,
                                String nickname,
                                LocalDate dateOfBirth,
                                Gender gender) {
    
}