package com.LetMeDoWith.LetMeDoWith.presentation.task.dto;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.UpdateDowithTaskWithRoutinesCommand;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import lombok.Builder;

@Builder
public record UpdateDowithTaskReqDto(
    String title,
    Long taskCategoryId,
    LocalDate date,
    LocalTime startTime,
    Boolean isRoutine,
    List<LocalDate> routineDates
) {

  public UpdateDowithTaskWithRoutinesCommand toCommand() {
    return UpdateDowithTaskWithRoutinesCommand.builder()
        .title(this.title)
        .taskCategoryId(this.taskCategoryId)
        .date(this.date)
        .startTime(this.startTime)
        .isRoutine(this.isRoutine)
        .routineDates(new HashSet<>(routineDates))
        .build();
  }

}
