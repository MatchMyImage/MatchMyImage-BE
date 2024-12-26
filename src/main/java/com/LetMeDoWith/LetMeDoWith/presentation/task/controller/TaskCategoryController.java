package com.LetMeDoWith.LetMeDoWith.presentation.task.controller;

import com.LetMeDoWith.LetMeDoWith.application.task.service.TaskCategoryService;
import com.LetMeDoWith.LetMeDoWith.common.util.AuthUtil;
import com.LetMeDoWith.LetMeDoWith.common.util.ResponseUtil;
import com.LetMeDoWith.LetMeDoWith.presentation.task.dto.GetAllTaskCategoryRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Task Category", description = "테스크 카테고리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task/category")
public class TaskCategoryController {
    
    @Autowired
    private TaskCategoryService taskCategoryService;

    @Operation(summary = "테스크 카테고리 조회", description = "테스트 카테고리를 조회합니다.")
    @GetMapping("")
    public ResponseEntity getAllTaskCategories() {
        
        Long memberId = AuthUtil.getMemberId();
        
        List<GetAllTaskCategoryRes> res = taskCategoryService.getAllCategory(memberId)
                                                             .stream()
                                                             .map(GetAllTaskCategoryRes::from)
                                                             .toList();
        
        return ResponseUtil.createSuccessResponse(res);
    }
}