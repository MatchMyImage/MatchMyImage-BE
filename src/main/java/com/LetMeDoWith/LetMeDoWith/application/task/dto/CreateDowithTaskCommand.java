package com.LetMeDoWith.LetMeDoWith.application.task.dto;

import com.LetMeDoWith.LetMeDoWith.presentation.task.dto.CreateDowithTaskReqDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;

@Builder
public record CreateDowithTaskCommand(
    String title,
    Long categoryId,
    LocalDateTime startDateTime,
    Boolean isRoutine,
    RoutineInfo routineInfo
) {

  public static CreateDowithTaskCommand of(CreateDowithTaskReqDto dto) {
    return CreateDowithTaskCommand.builder()
        .title(dto.title())
        .categoryId(dto.categoryId())
        .startDateTime(dto.startDateTime())
        .isRoutine(dto.isRoutine())
        .routineInfo(RoutineInfo.builder().build())
        .build();
  }

  @Builder(access = AccessLevel.PRIVATE)
  public static record RoutineInfo () {
    // TODO - 정의 필요
  }
}
