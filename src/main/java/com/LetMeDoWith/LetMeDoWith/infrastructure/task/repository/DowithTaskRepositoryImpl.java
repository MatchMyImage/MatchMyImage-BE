package com.LetMeDoWith.LetMeDoWith.infrastructure.task.repository;

import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTaskRoutine;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRepository;
import com.LetMeDoWith.LetMeDoWith.infrastructure.task.jpaRepository.DowithTaskConfirmJpaRepository;
import com.LetMeDoWith.LetMeDoWith.infrastructure.task.jpaRepository.DowithTaskJpaRepository;
import com.LetMeDoWith.LetMeDoWith.infrastructure.task.jpaRepository.DowithTaskRoutineJpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DowithTaskRepositoryImpl implements DowithTaskRepository {

  private final DowithTaskJpaRepository dowithTaskJpaRepository;
  private final DowithTaskRoutineJpaRepository dowithTaskRoutineJpaRepository;
  private final DowithTaskConfirmJpaRepository dowithTaskConfirmJpaRepository;

  @Override
  public Optional<DowithTask> getDowithTask(Long id, Long memberId) {
    return dowithTaskJpaRepository.findDowithTaskAggregate(id, memberId);
  }

  @Override
  public List<DowithTask> getDowithTasks(Long memberId, LocalDate date) {
    return dowithTaskJpaRepository.findAllDowithTaskAggregates(memberId, date);
  }

  @Override
  public List<DowithTask> getDowithTasks(Long memberId, Set<LocalDate> dates) {
    return dowithTaskJpaRepository.findAllDowithTaskAggregates(memberId, dates);
  }


  @Override
  public List<DowithTask> getDowithTasks(DowithTaskRoutine dowithTaskRoutine) {
    return null; // TODO - 구현 필요
  }

  @Override
  public DowithTask saveDowithTask(DowithTask dowithTask) {
    return dowithTaskJpaRepository.save(dowithTask);
  }

  @Override
  public List<DowithTask> saveDowithTasks(List<DowithTask> dowithTasks) {
    return dowithTaskJpaRepository.saveAll(dowithTasks);
  }

  @Override
  public void delete(List<DowithTask> dowithTasks) {
    dowithTaskJpaRepository.deleteAll(dowithTasks);
  }
}
