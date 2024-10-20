package com.LetMeDoWith.LetMeDoWith.presentation.task.dto;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.DowithTaskRoutineInfoVO;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.UpdateDowithTaskCommand;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
public record UpdateDowithTaskReqDto(
    String title,
    Long taskCategoryId,
    LocalDateTime startDateTime,
    Boolean isRoutine,
    RoutineInfo routineInfo
) {

  @Data
  public static record RoutineInfo () {
    // TODO - 정의 필요
  }

  public UpdateDowithTaskCommand toCommand() {
    return UpdateDowithTaskCommand.builder()
        .title(this.title)
        .taskCategoryId(this.taskCategoryId)
        .startDateTime(this.startDateTime)
        .isRoutine(this.isRoutine)
        .routineInfo(DowithTaskRoutineInfoVO.builder()
            // TODO 정의 필요
            .build())
        .build();
  }

}
