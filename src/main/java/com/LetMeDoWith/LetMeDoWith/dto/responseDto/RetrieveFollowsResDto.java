package com.LetMeDoWith.LetMeDoWith.dto.responseDto;

import java.util.List;

import lombok.Builder;

@Builder
public record RetrieveFollowsResDto(List<Follow> follows) {

	@Builder
	public record Follow(Long id, String nickname, String selfDescription, String profileImageUrl) {
	}
}
