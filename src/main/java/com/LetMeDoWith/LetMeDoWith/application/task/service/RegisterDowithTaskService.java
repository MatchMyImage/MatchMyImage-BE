package com.LetMeDoWith.LetMeDoWith.application.task.service;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_CREATE_COUNT_EXCEED;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.CreateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.CreateDowithTaskWithRoutineCommand;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRepository;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    Set<LocalDate> targetDateSet = new HashSet<>();
    targetDateSet.add(command.date());

    // 두윗모드 사용가능한지 validate
    if(!DowithTask.checkRegisterAvailable(dowithTaskRepository.getDowithTasks(memberId, targetDateSet), targetDateSet)) {
      throw new RestApiException(DOWITH_TASK_CREATE_COUNT_EXCEED);
    }

    DowithTask dowithTask = DowithTask.createOf(memberId, command.taskCategoryId(), command.title(), command.date(), command.startTime());

    return dowithTaskRepository.saveDowithTask(dowithTask);

  }

  @Transactional
  public List<DowithTask> registerDowithTaskWithRoutine(Long memberId, CreateDowithTaskWithRoutineCommand command) {

    Set<LocalDate> tartgetDateSet = command.getTargetDateSet();
    if(!DowithTask.checkRegisterAvailable(
        dowithTaskRepository.getDowithTasks(memberId, tartgetDateSet), tartgetDateSet)) {
      throw new RestApiException(DOWITH_TASK_CREATE_COUNT_EXCEED);
    }

    List<DowithTask> dowithTask = DowithTask.createWithRoutineOf(memberId, command.taskCategoryId(),
        command.title(), command.date(), command.startTime(), command.routineDates());

    return dowithTaskRepository.saveDowithTasks(dowithTask);

  }



}
