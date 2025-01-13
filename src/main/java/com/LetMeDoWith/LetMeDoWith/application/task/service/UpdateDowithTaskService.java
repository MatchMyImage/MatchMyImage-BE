package com.LetMeDoWith.LetMeDoWith.application.task.service;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_NOT_EXIST;
import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_TASK_CATEGORY_NOT_EXIST;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.UpdateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.UpdateDowithTaskWithRoutinesCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.repository.TaskCategoryRepository;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRepository;
import com.LetMeDoWith.LetMeDoWith.domain.task.service.DowithTaskUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateDowithTaskService {

  private final DowithTaskUpdateService updateService;

  private final DowithTaskRepository dowithTaskRepository;
  private final TaskCategoryRepository taskCategoryRepository;

  /**
   * 두윗모드 Task 수정 루틴이 없는 케이스. 루틴이 설정된 경우 루틴 삭제
   *
   * @param memberId
   * @param command
   */
  @Transactional
  public void updateDowithTask(Long memberId, UpdateDowithTaskCommand command) {

    DowithTask dowithTask = dowithTaskRepository.getDowithTask(command.id(), memberId)
        .orElseThrow(() -> new RestApiException(DOWITH_TASK_NOT_EXIST));

    taskCategoryRepository.getActiveTaskCategory(command.taskCategoryId(), memberId)
        .orElseThrow(() -> new RestApiException(DOWITH_TASK_TASK_CATEGORY_NOT_EXIST));

    updateService.updateDowithTaskWithoutRoutine(dowithTask, command.title(),
        command.taskCategoryId(), command.date(), command.startTime());

  }

  /**
   * 두윗모드 테스크 수정
   *
   * @param memberId
   * @param command
   */
  @Transactional
  public void updateDowithTaskWithRoutines(Long memberId,
      UpdateDowithTaskWithRoutinesCommand command) {

    DowithTask dowithTask = dowithTaskRepository.getDowithTask(command.id(), memberId)
        .orElseThrow(() -> new RestApiException(DOWITH_TASK_NOT_EXIST));

    taskCategoryRepository.getActiveTaskCategory(command.taskCategoryId(), memberId)
        .orElseThrow(() -> new RestApiException(DOWITH_TASK_TASK_CATEGORY_NOT_EXIST));

    if (command.isRoutine()) {

      List<DowithTask> dowithTasksWithRoutines = dowithTaskRepository.getDowithTasks(memberId,
          dowithTask.getRoutineId());

      DowithTask.updateWithRoutines(dowithTasks, command.title(), command.taskCategoryId(),
          command.date(),
          command.startTime(), command.routineDates());

    } else {
      updateService.updateDowithTaskWithoutRoutine(dowithTask, command.title(),
          command.taskCategoryId(), command.date(), command.startTime());
    }

  }

}
