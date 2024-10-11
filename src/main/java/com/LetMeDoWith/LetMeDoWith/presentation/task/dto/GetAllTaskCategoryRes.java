package com.LetMeDoWith.LetMeDoWith.presentation.task.dto;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.TaskCategoryVO;
import com.LetMeDoWith.LetMeDoWith.domain.task.TaskCategory.TaskCategoryCreationType;
import java.util.List;

public record GetAllTaskCategoryRes(
    Long id,
    String title,
    TaskCategoryCreationType creationType,
    String emoji,
    Long categoryHolderId
) {
    
    public static GetAllTaskCategoryRes from(TaskCategoryVO taskCategoryVO) {
        return new GetAllTaskCategoryRes(taskCategoryVO.getId(),
                                         taskCategoryVO.getTitle(),
                                         taskCategoryVO.getCreationType(),
                                         taskCategoryVO.getEmoji(),
                                         taskCategoryVO.getCategoryHolderId());
    }
    
    public static List<GetAllTaskCategoryRes> from(List<TaskCategoryVO> voList) {
        return voList.stream().map(GetAllTaskCategoryRes::from).toList();
    }
    
}