package com.LetMeDoWith.LetMeDoWith.enums.attributeConverter.member;

import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.attributeConverter.AbstractAttributeConverter;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.util.EnumUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SocialProviderConverter extends AbstractAttributeConverter<SocialProvider> {

	public SocialProviderConverter() {
		super(SocialProvider.class);
	}

}