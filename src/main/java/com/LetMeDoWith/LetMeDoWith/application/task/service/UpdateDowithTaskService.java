package com.LetMeDoWith.LetMeDoWith.application.task.service;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_NOT_EXIST;
import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_TASK_CATEGORY_NOT_EXIST;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.UpdateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.repository.TaskCategoryRepository;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.TaskCategory;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRepository;
import com.LetMeDoWith.LetMeDoWith.domain.task.service.DowithTaskUpdateService;
import java.time.LocalDate;
import java.util.Set;
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
   * 두윗모드Task 내용 수정
   *
   * @param memberId
   * @param command
   */
  @Transactional
  public void updateDowithTaskContents(Long memberId, UpdateDowithTaskCommand command) {

    DowithTask dowithTask = dowithTaskRepository.getDowithTask(command.id(), memberId)
        .orElseThrow(() -> new RestApiException(DOWITH_TASK_NOT_EXIST));

    TaskCategory taskCategory = taskCategoryRepository.getActiveTaskCategory(
            command.taskCategoryId(), memberId)
        .orElseThrow(() -> new RestApiException(DOWITH_TASK_TASK_CATEGORY_NOT_EXIST));

    updateService.updateDowithTaskContents(dowithTask, command.title(),
        taskCategory, command.date(), command.startTime());

  }

  /**
   * 두윗모드Task 루틴 수정
   *
   * @param memberId
   * @param dowithTaskId
   * @param routineDates
   */
  @Transactional
  public void updateDowithTaskRoutine(Long memberId, Long dowithTaskId,
      Set<LocalDate> routineDates) {

    DowithTask dowithTask = dowithTaskRepository.getDowithTask(dowithTaskId, memberId)
        .orElseThrow(() -> new RestApiException(DOWITH_TASK_NOT_EXIST));

    updateService.updateDowithTaskRoutine(dowithTask, routineDates);

  }

}
