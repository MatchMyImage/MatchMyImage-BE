package com.LetMeDoWith.LetMeDoWith.dto.requestDto;

import lombok.Builder;

@Builder
public record CreateMemberTermAgreeReq(Boolean termsOfAgree,
                                       Boolean privacy,
                                       Boolean advertisement) {
    
}