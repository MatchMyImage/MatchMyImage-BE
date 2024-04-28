package com.LetMeDoWith.LetMeDoWith.dto.requestDto;

import lombok.Builder;

@Builder
public record CreateFollowReqDto(Long followMemberId) {
}
