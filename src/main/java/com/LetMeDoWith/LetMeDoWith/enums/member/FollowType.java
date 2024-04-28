package com.LetMeDoWith.LetMeDoWith.enums.member;

import com.LetMeDoWith.LetMeDoWith.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FollowType implements BaseEnum {

	FOLLOWER("FOLLOWER", "팔로워"),
	FOLLOWING("FOLLOWING", "팔로잉");


	private final String code;
	private final String description;
}
