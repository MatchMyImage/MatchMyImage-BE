package com.LetMeDoWith.LetMeDoWith.enums.converter.member;

import com.LetMeDoWith.LetMeDoWith.enums.converter.AbstractCombinedConverter;
import com.LetMeDoWith.LetMeDoWith.enums.member.FollowType;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter(autoApply = true)
@Component
public class FollowTypeConverter extends AbstractCombinedConverter<FollowType> {
	
	public FollowTypeConverter() {
		super(FollowType.class);
	}
	
}