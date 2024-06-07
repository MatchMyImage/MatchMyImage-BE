package com.LetMeDoWith.LetMeDoWith.dto.requestDto;

public record CreateMemberTermAgreeReq(Boolean termsOfAgree,
                                       Boolean privacy,
                                       Boolean advertisement) {
    
}