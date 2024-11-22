package com.LetMeDoWith.LetMeDoWith.presentation.task.dto;

import com.LetMeDoWith.LetMeDoWith.domain.task.enums.DowithTaskStatus;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateDowithTaskResDto(
    List<DowithTaskDto> dowithTaskDtos
) {

  @Builder
  public static record DowithTaskDto(
      Long id,
      Long taskCategoryId,
      String title,
      DowithTaskStatus status,
      LocalDate date,
      LocalTime startTime,
      Boolean isRoutine,
      List<LocalDate> routineDates
      ){

    public static DowithTaskDto from(DowithTask dowithTask) {
      return DowithTaskDto.builder()
          .id(dowithTask.getId())
          .taskCategoryId(dowithTask.getTaskCategoryId())
          .title(dowithTask.getTitle())
          .status(dowithTask.getStatus())
          .date(dowithTask.getDate())
          .startTime(dowithTask.getStartTime())
          .isRoutine(dowithTask.isRoutine())
          .routineDates(dowithTask.getRoutineDates().stream().toList())
          .build();
    }

  }

  public static CreateDowithTaskResDto toCreateDowithTaskResDto(List<DowithTask> savedDowithTasks) {
    ArrayList<DowithTaskDto> dowithTaskDtos = new ArrayList<DowithTaskDto>();
    return new CreateDowithTaskResDto(savedDowithTasks.stream().map(DowithTaskDto::from).toList());
  }
}
