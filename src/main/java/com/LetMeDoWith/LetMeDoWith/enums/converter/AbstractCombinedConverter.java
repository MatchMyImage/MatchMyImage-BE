package com.LetMeDoWith.LetMeDoWith.enums.converter;

import com.LetMeDoWith.LetMeDoWith.enums.BaseEnum;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.util.EnumUtil;
import jakarta.persistence.AttributeConverter;
import org.springframework.core.convert.converter.Converter;

/**
 * HTTP 요청을 변환하기 위한 Converter와, JPA의 AttributeConverter를 통합한 형태의 Converter.
 * <p>
 * 이 클래스를 상속하는 것으로 두개의 Converter를 동시에 구현할 수 있다.
 *
 * @param <T> Converter가 필요한 Enum
 */
public abstract class AbstractCombinedConverter<T extends BaseEnum>
    implements Converter<String, T>, AttributeConverter<T, String> {
    
    private final Class<T> targetClass;
    
    public AbstractCombinedConverter(Class<T> targetClass) {
        this.targetClass = targetClass;
    }
    
    // Spring Converter method
    @Override
    public T convert(String source) {
        try {
            return EnumUtil.getEnum(targetClass, source);
        } catch (Exception e) {
            throw new RestApiException(FailResponseStatus.BAD_REQUEST);
        }
    }
    
    // JPA AttributeConverter methods
    @Override
    public String convertToDatabaseColumn(T attribute) {
        return attribute.getCode();
    }
    
    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return EnumUtil.getEnum(targetClass, dbData);
        } catch (Exception e) {
            // 적절한 Exception 구현 후 대치
            throw new RuntimeException(e);
        }
    }
}