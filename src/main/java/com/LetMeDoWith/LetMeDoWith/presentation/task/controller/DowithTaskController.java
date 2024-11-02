package com.LetMeDoWith.LetMeDoWith.presentation.task.controller;

import com.LetMeDoWith.LetMeDoWith.application.task.service.RegisterDowithTaskService;
import com.LetMeDoWith.LetMeDoWith.application.task.service.UpdateDowithTaskService;
import com.LetMeDoWith.LetMeDoWith.common.util.AuthUtil;
import com.LetMeDoWith.LetMeDoWith.common.util.ResponseUtil;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import com.LetMeDoWith.LetMeDoWith.presentation.task.dto.CreateDowithTaskReqDto;
import com.LetMeDoWith.LetMeDoWith.presentation.task.dto.CreateDowithTaskResDto;
import com.LetMeDoWith.LetMeDoWith.presentation.task.dto.UpdateDowithTaskReqDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task/dowith")
@RequiredArgsConstructor
public class DowithTaskController {

  private final RegisterDowithTaskService registerDowithTaskService;
  private final UpdateDowithTaskService updateDowithTaskService;

  @GetMapping("")
  public ResponseEntity createDowithTask(@RequestBody CreateDowithTaskReqDto requestBody) {

    Long memberId = AuthUtil.getMemberId();

    List<DowithTask> dowithTasks = new ArrayList<>();
    if(requestBody.isRoutine()) {
      dowithTasks.addAll(registerDowithTaskService.registerDowithTaskWithRoutine(memberId, requestBody.toCreateDowithTaskRoutineCommand()));
    } else {
      dowithTasks.add(registerDowithTaskService.registerDowithTask(memberId, requestBody.toCreateDowithTaskCommand()));
    }


    return ResponseUtil.createSuccessResponse();

  }

  @PutMapping("")
  public ResponseEntity updateDowithTask(@RequestBody UpdateDowithTaskReqDto requestBody) {

    Long memberId =AuthUtil.getMemberId();

    updateDowithTaskService.updateDowithTask(memberId, requestBody.toCommand());

    return ResponseUtil.createSuccessResponse();

  }

}
