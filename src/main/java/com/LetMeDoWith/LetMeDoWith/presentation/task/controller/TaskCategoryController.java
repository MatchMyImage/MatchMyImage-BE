package com.LetMeDoWith.LetMeDoWith.presentation.task.controller;

import com.LetMeDoWith.LetMeDoWith.application.task.service.TaskCategoryService;
import com.LetMeDoWith.LetMeDoWith.common.util.ResponseUtil;
import com.LetMeDoWith.LetMeDoWith.presentation.task.dto.GetAllTaskCategoryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task/category")
public class TaskCategoryController {
    
    @Autowired
    private TaskCategoryService taskCategoryService;
    
    @GetMapping("")
    public ResponseEntity getAllTaskCategories(
        @RequestParam(required = false) Long holderId) {
        
        if (holderId != null) {
            return ResponseUtil.createSuccessResponse(
                GetAllTaskCategoryRes.from(taskCategoryService.getAllCategory(holderId)));
        } else {
            return ResponseUtil.createSuccessResponse(
                GetAllTaskCategoryRes.from(taskCategoryService.getAllMyCategory()));
        }
    }
}