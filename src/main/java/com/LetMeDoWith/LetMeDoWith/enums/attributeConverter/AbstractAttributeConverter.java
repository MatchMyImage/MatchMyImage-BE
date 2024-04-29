package com.LetMeDoWith.LetMeDoWith.enums.attributeConverter;

import com.LetMeDoWith.LetMeDoWith.enums.BaseEnum;
import com.LetMeDoWith.LetMeDoWith.util.EnumUtil;
import jakarta.persistence.AttributeConverter;


/**
 * Enum의 code attribute를 db에 String으로 변환하여 입력하는 converter. BaseEnum을 상속받는 모든 클래스에서 이 클래스를 상속함으로써
 * converter를 구현할 수 있다.
 *
 * @param <T> db에 컨버팅 할 Enum
 */

public class AbstractAttributeConverter<T extends BaseEnum> implements
    AttributeConverter<T, String> {

    private Class<T> targetClass;

    public AbstractAttributeConverter(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return attribute.getCode();
    }
    
    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return EnumUtil.getEnum(targetClass, dbData);
        } catch (Exception e) {
            // TODO: 적절한 Exception 구현 후 대치
            throw new RuntimeException(e);
        }
    }
}