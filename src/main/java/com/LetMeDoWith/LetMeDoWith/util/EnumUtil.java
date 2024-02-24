package com.LetMeDoWith.LetMeDoWith.util;

import com.LetMeDoWith.LetMeDoWith.enums.BaseEnum;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnumUtil {

	public <T extends BaseEnum> T getEnum(Class<T> enumClass, String code) throws Exception {
		if(code == null) throw new Exception(); // TODO - 추후 RestAPI exception 생기면 추가

		for(BaseEnum el : enumClass.getEnumConstants()) {
			if(code.equalsIgnoreCase(el.getCode().toUpperCase())) {
				return (T) el;
			}
		}

		throw new Exception(); // TODO - 추후 RestAPI exception 생기면 추가
	}
}
