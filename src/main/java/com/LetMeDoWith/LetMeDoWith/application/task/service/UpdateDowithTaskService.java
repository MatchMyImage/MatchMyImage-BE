package com.LetMeDoWith.LetMeDoWith.application.task.service;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_NOT_EXIST;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.UpdateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateDowithTaskService {

  private final DowithTaskRepository dowithTaskRepository;
  /**
   * 두윗모드 테스크 수정
   * @param memberId
   * @param command
   */
  @Transactional
  public void updateDowithTask(Long memberId, UpdateDowithTaskCommand command) {

    DowithTask dowithTask = dowithTaskRepository.getDowithTask(command.id(), memberId)
        .orElseThrow(() -> new RestApiException(DOWITH_TASK_NOT_EXIST));

    dowithTask.updateInfo(command.title(), command.taskCategoryId(), LocalDateTime.of(command.startDate(), command.startTime()));

    if(dowithTask.isDifferent(LocalDateTime.of(command.startDate(), command.startTime()), command.routineDates())) {

    }

  }

}
