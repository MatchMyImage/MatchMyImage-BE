package com.LetMeDoWith.LetMeDoWith.presentation.task.dto;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.CreateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.CreateDowithTaskWithRoutineCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record CreateDowithTaskReqDto(
    @NotBlank @Size(max = 40) String title,
    Long taskCategoryId,
    @NotNull LocalDateTime startDateTime,
    @NotNull Boolean isRoutine,
    List<LocalDate> routineDates
) {

  public CreateDowithTaskCommand toCreateDowithTaskCommand() {
    return CreateDowithTaskCommand.builder()
        .title(this.title)
        .taskCategoryId(this.taskCategoryId)
        .date(this.startDateTime.toLocalDate())
        .startTime(this.startDateTime.toLocalTime())
        .build();
  }

  public CreateDowithTaskWithRoutineCommand toCreateDowithTaskRoutineCommand() {
    return CreateDowithTaskWithRoutineCommand.builder()
        .title(this.title)
        .taskCategoryId(this.taskCategoryId)
        .date(this.startDateTime.toLocalDate())
        .startTime(this.startDateTime.toLocalTime())
        .routineDates(routineDates != null ? Set.copyOf(this.routineDates) : new HashSet<>())
        .build();
  }
}
