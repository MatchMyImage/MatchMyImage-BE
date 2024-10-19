package com.LetMeDoWith.LetMeDoWith.application.task.repository;

import com.LetMeDoWith.LetMeDoWith.domain.task.DowithTask;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DowithTaskRepository {

  Optional<DowithTask> getDowithTask(Long id);
  List<DowithTask> getDowithTasks(LocalDate startDate);
  DowithTask saveDowithTask(DowithTask dowithTask);

}
