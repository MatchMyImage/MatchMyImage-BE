package com.LetMeDoWith.LetMeDoWith.application.task.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record CreateDowithTaskCommand (
  String title,
  Long taskCategoryId,
  LocalDate startDate,
  LocalTime startTime
){}
