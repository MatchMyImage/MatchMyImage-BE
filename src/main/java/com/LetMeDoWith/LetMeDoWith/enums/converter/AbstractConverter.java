package com.LetMeDoWith.LetMeDoWith.enums.converter;

import org.springframework.core.convert.converter.Converter;

import com.LetMeDoWith.LetMeDoWith.enums.BaseEnum;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.util.EnumUtil;

public class AbstractConverter<T extends BaseEnum> implements Converter<String, T> {

	private Class<T> targetClass;

	public AbstractConverter(Class<T> targetClass) {
		this.targetClass = targetClass;
	}

	@Override
	public T convert(String source) {
		try{
			return EnumUtil.getEnum(targetClass, source);
		}catch (Exception e) {
			throw new RestApiException(FailResponseStatus.BAD_REQUEST);
		}
	}
}
