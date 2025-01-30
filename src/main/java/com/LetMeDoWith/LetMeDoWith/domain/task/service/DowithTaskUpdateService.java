package com.LetMeDoWith.LetMeDoWith.domain.task.service;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_CREATE_COUNT_EXCEED;
import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_UPDATE_NOT_AVAIL;

import com.LetMeDoWith.LetMeDoWith.common.annotation.DomainService;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTaskRoutine;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.TaskCategory;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRepository;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRoutineRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class DowithTaskUpdateService {

  private final DowithTaskRegisterAvailService registerAvailService;

  private final DowithTaskRepository dowithTaskRepository;
  private final DowithTaskRoutineRepository dowithTaskRoutineRepository;

  public void updateDowithTaskContents(DowithTask dowithTask, String title,
      TaskCategory taskCategory, LocalDate date,
      LocalTime startTime) {

    if (!dowithTask.isContentsEditable()) {
      throw new RestApiException(DOWITH_TASK_UPDATE_NOT_AVAIL);
    }

    if (dowithTask.isRoutine()) {
      // date 기준으로 업데이트 대상 판별
      Map<Boolean, List<DowithTask>> routineDowithTaskMap = new HashMap<>();
      routineDowithTaskMap.put(true, new ArrayList<>());
      routineDowithTaskMap.put(false, new ArrayList<>());
      dowithTaskRepository.getDowithTasks(dowithTask.getRoutine()).forEach(task -> {
        boolean isUpdateTarget = !task.getDate().isBefore(LocalDate.now());
        routineDowithTaskMap.get(isUpdateTarget).add(task);
      });

      // 기존 routine 삭제
      dowithTaskRoutineRepository.delete(dowithTask.getRoutine());

      // 과거 Task 루틴 삭제
      routineDowithTaskMap.get(false).forEach(DowithTask::deleteRoutine);

      // 현재, 미래 Task 콘텐츠 + 루틴 변경
      DowithTaskRoutine newRoutine = dowithTaskRoutineRepository.save(DowithTaskRoutine.from(
          routineDowithTaskMap.get(true).stream().map(DowithTask::getDate)
              .collect(Collectors.toSet())));
      routineDowithTaskMap.get(true)
          .forEach(task -> task.update(title, taskCategory.getId(), date, startTime, newRoutine));

    } else {

      dowithTask.updateContent(title, taskCategory.getId(), date, startTime);

    }

  }

  public void updateDowithTaskRoutine(DowithTask dowithTask, Set<LocalDate> routineDates) {

    if (!registerAvailService.isRegisterAvail(routineDates,
        dowithTaskRepository.getDowithTasks(dowithTask.getMemberId(), routineDates)).isAvail()) {
      throw new RestApiException(DOWITH_TASK_CREATE_COUNT_EXCEED);
    }

    if (dowithTask.isRoutine()) {

      Map<Boolean, List<DowithTask>> routineDowithTaskMap = new HashMap<>();
      routineDowithTaskMap.put(true, new ArrayList<>());
      routineDowithTaskMap.put(false, new ArrayList<>());
      dowithTaskRepository.getDowithTasks(dowithTask.getRoutine()).forEach(task -> {
        boolean isUpdateTarget = !task.getDate().isBefore(LocalDate.now());
        routineDowithTaskMap.get(isUpdateTarget).add(task);
      });

      // 기존 routine 삭제
      dowithTaskRoutineRepository.delete(dowithTask.getRoutine());

      // 과거 Task 루틴 삭제
      routineDowithTaskMap.get(false).forEach(DowithTask::deleteRoutine);

      // 현재, 미래 루틴 변경
      DowithTaskRoutine newRoutine = dowithTaskRoutineRepository.save(DowithTaskRoutine.from(
          routineDowithTaskMap.get(true).stream().map(DowithTask::getDate)
              .collect(Collectors.toSet())));
      routineDowithTaskMap.get(true).forEach(task -> task.updateRoutine(newRoutine));

    } else {

      dowithTaskRoutineRepository.delete(dowithTask.getRoutine());
      dowithTask.createRoutine(routineDates);

    }

  }

}

