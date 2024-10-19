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
    RoutineInfoVO routineInfo
) {

  public static CreateDowithTaskCommand of(CreateDowithTaskReqDto dto) {
    return CreateDowithTaskCommand.builder()
        .title(dto.title())
        .categoryId(dto.categoryId())
        .startDateTime(dto.startDateTime())
        .isRoutine(dto.isRoutine())
        .routineInfo(RoutineInfoVO.builder()
            // TODO - 정의 필요
            .build())
        .build();
  }

}
