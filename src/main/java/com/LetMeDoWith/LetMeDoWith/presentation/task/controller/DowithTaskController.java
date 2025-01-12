package com.LetMeDoWith.LetMeDoWith.presentation.task.controller;

import com.LetMeDoWith.LetMeDoWith.application.task.service.RegisterDowithTaskService;
import com.LetMeDoWith.LetMeDoWith.application.task.service.UpdateDowithTaskService;
import com.LetMeDoWith.LetMeDoWith.common.util.AuthUtil;
import com.LetMeDoWith.LetMeDoWith.common.util.ResponseUtil;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import com.LetMeDoWith.LetMeDoWith.presentation.task.dto.CreateDowithTaskReqDto;
import com.LetMeDoWith.LetMeDoWith.presentation.task.dto.CreateDowithTaskResDto;
import com.LetMeDoWith.LetMeDoWith.presentation.task.dto.UpdateDowithTaskReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dowith Task", description = "두윗모드 테스크")
@RestController
@RequestMapping("/api/v1/task/dowith")
@RequiredArgsConstructor
public class DowithTaskController {

  private final RegisterDowithTaskService registerDowithTaskService;
  private final UpdateDowithTaskService updateDowithTaskService;

  @Operation(summary = "두윗모드 테스트 생성", description = "두윗모드 테스크를 생성합니다.")
  @PostMapping("")
  public ResponseEntity createDowithTask(@RequestBody CreateDowithTaskReqDto requestBody) {

    Long memberId = AuthUtil.getMemberId();

    List<DowithTask> dowithTasks = new ArrayList<>();
    if (requestBody.isRoutine()) {
      dowithTasks.addAll(registerDowithTaskService.registerDowithTaskWithRoutine(memberId,
          requestBody.toCreateDowithTaskRoutineCommand()));
    } else {
      dowithTasks.add(registerDowithTaskService.registerDowithTask(memberId,
          requestBody.toCreateDowithTaskCommand()));
    }

    return ResponseUtil.createSuccessResponse(
        CreateDowithTaskResDto.toCreateDowithTaskResDto(dowithTasks));
  }

  @Operation(summary = "두윗모드 테스트 수정", description = "두윗모드 테스크를 수정합니다.")
  @PutMapping("")
  public ResponseEntity updateDowithTask(@RequestBody UpdateDowithTaskReqDto requestBody) {

    Long memberId = AuthUtil.getMemberId();

    updateDowithTaskService.updateDowithTaskWithRoutines(memberId, requestBody.toCommand());

    return ResponseUtil.createSuccessResponse();

  }

}
