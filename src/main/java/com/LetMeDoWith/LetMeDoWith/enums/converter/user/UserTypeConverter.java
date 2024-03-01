package com.LetMeDoWith.LetMeDoWith.enums.converter.user;

import com.LetMeDoWith.LetMeDoWith.enums.user.UserType;
import com.LetMeDoWith.LetMeDoWith.util.EnumUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserTypeConverter implements AttributeConverter<UserType, String> {
    @Override
    public String convertToDatabaseColumn(UserType attribute) {
        return attribute.getCode();
    }

    @Override
    public UserType convertToEntityAttribute(String dbData) {
        try {
            return EnumUtil.getEnum(UserType.class, dbData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
