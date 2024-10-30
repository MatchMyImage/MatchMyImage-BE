package com.LetMeDoWith.LetMeDoWith.domain.task.repository;

import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DowithTaskRepository {

  Optional<DowithTask> getDowithTask(Long id, Long memberId);
  List<DowithTask> getDowithTasks(Long memberId, LocalDate date);
  List<DowithTask> getDowithTasks(Long memberId, List<LocalDate> dates);
  DowithTask saveDowithTask(DowithTask dowithTask);
  List<DowithTask> saveDowithTasks(List<DowithTask> dowithTasks);

}
