package com.LetMeDoWith.LetMeDoWith.presentation.member.dto;

import lombok.Builder;

@Builder
public record CreateMemberTermAgreeReqDto(Boolean termsOfAgree,
										  Boolean privacy,
										  Boolean advertisement) {
    
}