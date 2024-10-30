package com.LetMeDoWith.LetMeDoWith.application.task.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateDowithTaskWithRoutineCommand(
    String title,
    Long taskCategoryId,
    LocalDateTime startDateTime,
    Boolean isRoutine,
    List<LocalDate> routineDates
) {

}
