package com.LetMeDoWith.LetMeDoWith.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialProvider implements BaseEnum {

	KAKAO("KAKAO", "카카오"),
	GOOGLE("GOOGLE", "구글"),
	APPLE("APPLE", "애플");

	private final String code;
	private final String description;

}
