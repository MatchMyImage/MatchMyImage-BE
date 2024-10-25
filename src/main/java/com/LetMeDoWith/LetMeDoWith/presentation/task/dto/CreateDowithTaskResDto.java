package com.LetMeDoWith.LetMeDoWith.presentation.task.dto;

import com.LetMeDoWith.LetMeDoWith.common.enums.task.DowithTaskStatus;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateDowithTaskResDto(
    List<DowithTaskVO> dowithTasks
) {

  @Builder
  public static record DowithTaskVO (
      Long id,
      Long taskCategoryId,
      String title,
      DowithTaskStatus status,

  )

}
