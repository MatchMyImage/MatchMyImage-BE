package com.LetMeDoWith.LetMeDoWith.presentation.task.dto;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.DowithTaskRoutineInfoVO;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.UpdateDowithTaskCommand;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
public record UpdateDowithTaskReqDto(
    String title,
    Long taskCategoryId,
    LocalDateTime startDateTime,
    Boolean isRoutine,
    List<LocalDate> routineDates
) {

  public UpdateDowithTaskCommand toCommand() {
    return UpdateDowithTaskCommand.builder()
        .title(this.title)
        .taskCategoryId(this.taskCategoryId)
        .startDate(this.startDateTime.toLocalDate())
        .startTime(this.startDateTime.toLocalTime())
        .isRoutine(this.isRoutine)
        .routineDates(routineDates)
        .build();
  }

}
