package com.LetMeDoWith.LetMeDoWith.presentation.task.dto;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.DowithTaskRoutineInfoVO;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.UpdateDowithTaskCommand;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Builder
public record UpdateDowithTaskReqDto(
    String title,
    Long taskCategoryId,
    LocalDate date,
    LocalTime startTime,
    Boolean isRoutine,
    List<LocalDate> routineDates
) {

  public UpdateDowithTaskCommand toCommand() {
    return UpdateDowithTaskCommand.builder()
        .title(this.title)
        .taskCategoryId(this.taskCategoryId)
        .date(this.date)
        .startTime(this.startTime)
        .isRoutine(this.isRoutine)
        .routineDates(new HashSet<>(routineDates))
        .build();
  }

}
