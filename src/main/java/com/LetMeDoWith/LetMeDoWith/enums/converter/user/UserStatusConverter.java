package com.LetMeDoWith.LetMeDoWith.enums.converter.user;

import com.LetMeDoWith.LetMeDoWith.enums.user.UserStatus;
import com.LetMeDoWith.LetMeDoWith.util.EnumUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {
    @Override
    public String convertToDatabaseColumn(UserStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public UserStatus convertToEntityAttribute(String dbData) {
        try {
            return EnumUtil.getEnum(UserStatus.class, dbData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
