package com.LetMeDoWith.LetMeDoWith.application.task.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import lombok.Builder;

@Builder
public record UpdateDowithTaskCommand(
    Long id,
    String title,
    Long taskCategoryId,
    LocalDate date,
    LocalTime startTime,
    Boolean isRoutine,
    Set<LocalDate> routineDates
) {

}
