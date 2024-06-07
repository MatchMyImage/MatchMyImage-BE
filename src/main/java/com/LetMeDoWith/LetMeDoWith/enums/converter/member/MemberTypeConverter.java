package com.LetMeDoWith.LetMeDoWith.enums.converter.member;

import com.LetMeDoWith.LetMeDoWith.enums.converter.AbstractCombinedConverter;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberType;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter(autoApply = true)
@Component
public class MemberTypeConverter extends AbstractCombinedConverter<MemberType> {
    
    public MemberTypeConverter() {
        super(MemberType.class);
    }
    
}