package com.LetMeDoWith.LetMeDoWith.application.task.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateDowithTaskCommand(
    Long id,
    String title,
    Long taskCategoryId,
    LocalDate date,
    LocalTime startTime) {

}
