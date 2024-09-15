package com.LetMeDoWith.LetMeDoWith.common.converter.member;

import com.LetMeDoWith.LetMeDoWith.common.converter.AbstractCombinedConverter;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.TaskLevel;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TaskLevelConverter extends AbstractCombinedConverter<TaskLevel> {

  public TaskLevelConverter() { super(TaskLevel.class); }

}
