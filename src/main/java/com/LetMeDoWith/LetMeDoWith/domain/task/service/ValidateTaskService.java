package com.LetMeDoWith.LetMeDoWith.domain.task.service;

import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ValidateTaskService {

  public boolean isRegisterAvailable(List<DowithTask> dowithTasks, LocalDate targetDate) {

    Map<LocalDate, List<DowithTask>> dowithTaskMap = dowithTasks.stream()
        .collect(Collectors.groupingBy(e -> e.getStartDateTime().toLocalDate()));

    return !dowithTaskMap.containsKey(targetDate);

  }

}
