package com.LetMeDoWith.LetMeDoWith.application.task.service;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_CREATE_COUNT_EXCEED;
import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_NOT_EXIST;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.CreateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.UpdateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.repository.DowithTaskRepository;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.domain.task.DowithTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DowithTaskService {

  private final DowithTaskRepository dowithTaskRepository;

  /**
   * 두윗모드 테스크 생성
   * @param memberId
   * @param command
   */
  public DowithTask createDowithTask(Long memberId, CreateDowithTaskCommand command) {

    // 두윗모드 사용가능한지 validate
    if(!dowithTaskRepository.getDowithTasks(memberId, command.startDateTime().toLocalDate()).isEmpty()) {
      throw new RestApiException(DOWITH_TASK_CREATE_COUNT_EXCEED);
    }

    DowithTask dowithTask = DowithTask.ofInit(memberId, command.taskCategoryId(), command.title(),
        command.startDateTime());

    if(command.isRoutine()) {
      // 루틴설정
    }

    return dowithTaskRepository.saveDowithTask(dowithTask);

  }

  /**
   * 두윗모드 테스크 수정
   * @param memberId
   * @param command
   */
  public void updateDowithTask(Long memberId, UpdateDowithTaskCommand command) {

    DowithTask dowithTask = dowithTaskRepository.getDowithTask(command.id(), memberId)
        .orElseThrow(() -> new RestApiException(DOWITH_TASK_NOT_EXIST));

    dowithTask.update(command.title(), command.taskCategoryId(), command.startDateTime(), command.isRoutine(), command.routineInfo());

  }

}
