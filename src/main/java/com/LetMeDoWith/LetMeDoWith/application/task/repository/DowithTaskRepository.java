package com.LetMeDoWith.LetMeDoWith.application.task.repository;

import com.LetMeDoWith.LetMeDoWith.domain.task.DowithTask;
import java.util.Optional;

public interface DowithTaskRepository {

  Optional<DowithTask> getDowithTask(Long id);
  DowithTask saveDowithTask(DowithTask dowithTask);

}
