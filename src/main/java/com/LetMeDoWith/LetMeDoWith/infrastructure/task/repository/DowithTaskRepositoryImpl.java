package com.LetMeDoWith.LetMeDoWith.infrastructure.task.repository;

import com.LetMeDoWith.LetMeDoWith.application.task.repository.DowithTaskRepository;
import com.LetMeDoWith.LetMeDoWith.domain.task.DowithTask;
import com.LetMeDoWith.LetMeDoWith.domain.task.DowithTaskRoutine;
import com.LetMeDoWith.LetMeDoWith.infrastructure.task.jpaRepository.DowithTaskConfirmJpaRepository;
import com.LetMeDoWith.LetMeDoWith.infrastructure.task.jpaRepository.DowithTaskJpaRepository;
import com.LetMeDoWith.LetMeDoWith.infrastructure.task.jpaRepository.DowithTaskRoutineJpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DowithTaskRepositoryImpl implements DowithTaskRepository {

  private final DowithTaskJpaRepository dowithTaskJpaRepository;
  private final DowithTaskRoutineJpaRepository dowithTaskRoutineJpaRepository;
  private final DowithTaskConfirmJpaRepository dowithTaskConfirmJpaRepository;

  @Override
  public Optional<DowithTask> getDowithTask(Long id) {
    return dowithTaskJpaRepository.findJoinRoutineAndConfirm(id);
  }

  @Override
  public List<DowithTask> getDowithTasks(LocalDate startDate) {
    return null;
  }

  @Override
  public DowithTask saveDowithTask(DowithTask dowithTask) {
    return dowithTaskJpaRepository.save(dowithTask);
  }
}
