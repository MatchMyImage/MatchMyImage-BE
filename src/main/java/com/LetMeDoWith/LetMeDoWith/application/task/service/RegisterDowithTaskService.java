package com.LetMeDoWith.LetMeDoWith.application.task.service;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_CREATE_COUNT_EXCEED;
import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_NOT_EXIST;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.CreateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.CreateDowithTaskWithRoutineCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.UpdateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRepository;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterDowithTaskService {

  private final DowithTaskRepository dowithTaskRepository;

  /**
   * 두윗모드 테스크 생성
   * @param memberId
   * @param command
   */
  public DowithTask registerDowithTask(Long memberId, CreateDowithTaskCommand command) {

    // 두윗모드 사용가능한지 validate
    if(!DowithTask.validateRegisterAvailable(
        dowithTaskRepository.getDowithTasks(memberId, command.startDateTime().toLocalDate()))) {
      throw new RestApiException(DOWITH_TASK_CREATE_COUNT_EXCEED);
    }

    DowithTask dowithTask = DowithTask.create(memberId, command.taskCategoryId(), command.title(),
        command.startDateTime());

    return dowithTaskRepository.saveDowithTask(dowithTask);

  }

  @Transactional
  public List<DowithTask> registerDowithTaskWithRoutine(Long memberId, CreateDowithTaskWithRoutineCommand command) {

    // 두윗모드 사용가능한지 validate
    if(!DowithTask.validateRegisterAvailable(
        dowithTaskRepository.getDowithTasks(memberId, command.startDateTime().toLocalDate()))) {
      throw new RestApiException(DOWITH_TASK_CREATE_COUNT_EXCEED);
    }

    List<DowithTask> dowithTask = DowithTask.createWithRoutine(memberId, command.taskCategoryId(),
        command.title(), command.startDateTime());

    return dowithTaskRepository.saveDowithTasks(dowithTask);

  }

  /**
   * 두윗모드 테스크 수정
   * @param memberId
   * @param command
   */
  @Transactional
  public void updateDowithTask(Long memberId, UpdateDowithTaskCommand command) {

    DowithTask dowithTask = dowithTaskRepository.getDowithTask(command.id(), memberId)
        .orElseThrow(() -> new RestApiException(DOWITH_TASK_NOT_EXIST));

    dowithTask.update(command.title(), command.taskCategoryId(), command.startDateTime(), command.isRoutine(), command.routineInfo());

  }

}
