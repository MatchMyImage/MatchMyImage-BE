package com.LetMeDoWith.LetMeDoWith.application.task.service;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.CreateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.repository.DowithTaskRepository;
import com.LetMeDoWith.LetMeDoWith.domain.task.DowithTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DowithTaskService {

  private final DowithTaskRepository dowithTaskRepository;

  /**
   * 두윗모드 테스트 생성
   * @param memberId
   * @param command
   */
  public void createDowithTask(Long memberId, CreateDowithTaskCommand command) {

    // 두윗모드 사용가능한지 validate

    DowithTask dowithTask = DowithTask.ofInit(memberId, command.categoryId(), command.title(),
        command.startDateTime());

    if(command.isRoutine()) {
      // 루틴설정
    }

    dowithTaskRepository.saveDowithTask(dowithTask);

  }

}
