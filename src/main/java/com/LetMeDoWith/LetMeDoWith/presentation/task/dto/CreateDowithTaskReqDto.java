package com.LetMeDoWith.LetMeDoWith.presentation.task.dto;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.CreateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.CreateDowithTaskWithRoutineCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.DowithTaskRoutineInfoVO;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public record CreateDowithTaskReqDto(
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

  public CreateDowithTaskCommand toCreateDowithTaskCommand() {
    return CreateDowithTaskCommand.builder()
        .title(this.title)
        .taskCategoryId(this.taskCategoryId)
        .startDateTime(this.startDateTime)
        .build();
  }

  public CreateDowithTaskWithRoutineCommand toCreateDowithTaskRoutineCommand() {
    return CreateDowithTaskWithRoutineCommand.builder()
        .title(this.title)
        .taskCategoryId(this.taskCategoryId)
        .startDateTime(this.startDateTime)
        .routineInfo(DowithTaskRoutineInfoVO.builder()
            .build()) // TODO - 정의 필요
        .build();
  }
}
