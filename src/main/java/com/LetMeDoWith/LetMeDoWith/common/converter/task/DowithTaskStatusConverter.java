package com.LetMeDoWith.LetMeDoWith.common.converter.task;

import com.LetMeDoWith.LetMeDoWith.common.converter.AbstractCombinedConverter;
import com.LetMeDoWith.LetMeDoWith.domain.task.enums.DowithTaskStatus;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter(autoApply = true)
public class DowithTaskStatusConverter extends AbstractCombinedConverter<DowithTaskStatus> {

  public DowithTaskStatusConverter() {super(DowithTaskStatus.class);}
}
