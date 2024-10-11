package com.LetMeDoWith.LetMeDoWith.application.task.service;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.TaskCategoryConstants;
import com.LetMeDoWith.LetMeDoWith.application.task.dto.TaskCategoryVO;
import com.LetMeDoWith.LetMeDoWith.application.task.repository.TaskCategoryRepository;
import com.LetMeDoWith.LetMeDoWith.common.util.AuthUtil;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskCategoryService {
    
    private final TaskCategoryRepository taskCategoryRepository;
    
    public List<TaskCategoryVO> getAllCommonCategory() {
        return TaskCategoryConstants.COMMON_ALL;
    }
    
    /**
     * 한 멤버가 조회 가능한 모든 카테고리를 조회한다.
     *
     * @param memberId 조회하려는 멤버의 id
     * @return 멤버가 생성한 개인 카테고리 + 공통 카테고리
     */
    public List<TaskCategoryVO> getAllCategory(Long memberId) {
        List<TaskCategoryVO> userCategoryList = taskCategoryRepository.getCategories(memberId)
                                                                      .stream()
                                                                      .map(TaskCategoryVO::from)
                                                                      .toList();
        
        return Stream.concat(getAllCommonCategory().stream(),
                             userCategoryList.stream())
                     .toList();
    }
    
    /**
     * API를 요청하는 멤버가 조회 가능한 모든 카테고리를 조회한다.
     *
     * @return 요청한 멤버가 생성한 개인 카테고리 + 공통 카테고리
     */
    public List<TaskCategoryVO> getAllMyCategory() {
        Long memberId = AuthUtil.getMemberId();
        
        return getAllCategory(memberId);
    }
}