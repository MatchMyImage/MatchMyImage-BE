package com.LetMeDoWith.LetMeDoWith.application.task.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;

@Builder
public record CreateDowithTaskWithRoutineCommand(
    String title,
    Long taskCategoryId,
    LocalDate date,
    LocalTime startTime,
    Boolean isRoutine,
    Set<LocalDate> routineDates
) {
  public Set<LocalDate> getTargetDateSet() {
    Set<LocalDate> tartgetDateSet = new HashSet<>();
    tartgetDateSet.add(date);
    tartgetDateSet.addAll(routineDates);
    return tartgetDateSet;
  }
}
