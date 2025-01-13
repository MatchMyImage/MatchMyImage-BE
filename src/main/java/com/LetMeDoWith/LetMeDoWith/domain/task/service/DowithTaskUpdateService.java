package com.LetMeDoWith.LetMeDoWith.domain.task.service;

import com.LetMeDoWith.LetMeDoWith.common.annotation.DomainService;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTaskRoutine;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRepository;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRoutineRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class DowithTaskUpdateService {

  private final DowithTaskRepository dowithTaskRepository;
  private final DowithTaskRoutineRepository dowithTaskRoutineRepository;

  public void updateDowithTaskWithoutRoutine(DowithTask dowithTask, String title,
      Long taskCategoryId, LocalDate date,
      LocalTime startTime) {

    dowithTask.updateContent(title, taskCategoryId, date, startTime);
    DowithTaskRoutine toDeleteRoutine = dowithTask.deleteRoutine();
    dowithTaskRoutineRepository.delete(toDeleteRoutine);

  }

  public void updateDowithTaskWithRoutine(DowithTask dowithTask, String title, Long taskCategoryId,
      LocalDate date, LocalTime startTime, Set<LocalDate> routineDates) {

    Set<LocalDate> tartgetDateSet = new HashSet<>();
    tartgetDateSet.addAll(routineDates);
    tartgetDateSet.add(date);

    if (dowithTask.isRoutine()) {

      List<DowithTask> dowithTasks = dowithTaskRepository.getDowithTasks(dowithTask.getRoutine());

      // 기존 DowithTask 초기화 및 삭제 대상 선별
      // 기존 DowithTaskRoutine 삭제
      DowithTaskRoutine toDeleteRoutine = null;
      List<DowithTask> toDeleteTasks = new ArrayList<>();
      for (DowithTask task : dowithTasks) {
        toDeleteRoutine = task.deleteRoutine();
        if (!tartgetDateSet.contains(task.getDate())) {
          toDeleteTasks.add(task);
          dowithTasks.remove(task);
        }
      }
      dowithTaskRoutineRepository.delete(toDeleteRoutine);
      dowithTaskRepository.delete(toDeleteTasks);

      // 새로운 Routine 연결된 DowithTask 리스트 생성
      List<DowithTask> result = DowithTask.ofWithRoutine(dowithTasks,
          dowithTask.getMemberId(), taskCategoryId, title, date, startTime, routineDates);

      dowithTaskRepository.saveDowithTasks(result);

    } else {

      dowithTask.update(title, taskCategoryId, date, startTime, routineDates);

    }

  }

}

