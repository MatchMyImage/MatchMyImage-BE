package com.LetMeDoWith.LetMeDoWith.enums.attributeConverter.member;

import com.LetMeDoWith.LetMeDoWith.enums.attributeConverter.AbstractAttributeConverter;
import com.LetMeDoWith.LetMeDoWith.enums.member.Gender;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter extends AbstractAttributeConverter<Gender> {
    
    public GenderConverter() {
        super(Gender.class);
    }
}