package com.LetMeDoWith.LetMeDoWith.application.task.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UpdateDowithTaskCommand(
    Long id,
    String title,
    Long taskCategoryId,
    LocalDateTime startDateTime,
    Boolean isRoutine,
    DowithTaskRoutineInfoVO routineInfo
) {

}
