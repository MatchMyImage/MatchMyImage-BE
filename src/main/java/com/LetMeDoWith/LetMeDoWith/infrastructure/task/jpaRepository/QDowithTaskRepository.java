package com.LetMeDoWith.LetMeDoWith.infrastructure.task.jpaRepository;

import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QDowithTaskRepository {

  Optional<DowithTask> findDowithTaskAggregate(Long id);
  Optional<DowithTask> findDowithTaskAggregate(Long id, Long memberId);
  List<DowithTask> findDowithTaskAggregates(Long memberId, LocalDate date);
  List<DowithTask> findDowithTaskAggregates(Long memberId, Set<LocalDate> dates);
}
