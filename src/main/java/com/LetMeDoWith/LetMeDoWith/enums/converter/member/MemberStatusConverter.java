package com.LetMeDoWith.LetMeDoWith.enums.converter.member;

import com.LetMeDoWith.LetMeDoWith.enums.converter.AbstractCombinedConverter;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter(autoApply = true)
@Component
public class MemberStatusConverter extends AbstractCombinedConverter<MemberStatus> {
    
    public MemberStatusConverter() {
        super(MemberStatus.class);
    }
    
}