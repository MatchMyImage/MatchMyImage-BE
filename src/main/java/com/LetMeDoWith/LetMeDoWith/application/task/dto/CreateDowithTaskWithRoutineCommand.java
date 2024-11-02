package com.LetMeDoWith.LetMeDoWith.application.task.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import lombok.Builder;

@Builder
public record CreateDowithTaskWithRoutineCommand(
    String title,
    Long taskCategoryId,
    LocalDate startDate,
    LocalTime startTime,
    Boolean isRoutine,
    Set<LocalDate> routineDates
) {

}
