package com.LetMeDoWith.LetMeDoWith.application.task.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CreateDowithTaskCommand (
  String title,
  Long taskCategoryId,
  LocalDateTime startDateTime
){}
