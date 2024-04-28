package com.LetMeDoWith.LetMeDoWith.enums.converter.member;

import org.springframework.core.convert.converter.Converter;

import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.enums.converter.AbstractConverter;
import com.LetMeDoWith.LetMeDoWith.enums.member.FollowType;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.util.EnumUtil;

public class FollowTypeConverter extends AbstractConverter<FollowType> {

	public FollowTypeConverter() {
		super(FollowType.class);
	}

}
