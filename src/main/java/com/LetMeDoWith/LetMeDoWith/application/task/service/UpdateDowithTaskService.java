package com.LetMeDoWith.LetMeDoWith.application.task.service;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_CREATE_COUNT_EXCEED;
import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_NOT_EXIST;
import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_TASK_CATEGORY_NOT_EXIST;
import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_UPDATE_NOT_AVAIL;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.UpdateDowithTaskCommand;
import com.LetMeDoWith.LetMeDoWith.application.task.repository.TaskCategoryRepository;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTaskRoutine;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.TaskCategory;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRepository;
import com.LetMeDoWith.LetMeDoWith.domain.task.repository.DowithTaskRoutineRepository;
import com.LetMeDoWith.LetMeDoWith.domain.task.service.DowithTaskRegisterAvailService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateDowithTaskService {
    
    private final DowithTaskRegisterAvailService registerAvailService;
    
    private final DowithTaskRepository dowithTaskRepository;
    private final DowithTaskRoutineRepository dowithTaskRoutineRepository;
    
    private final TaskCategoryRepository taskCategoryRepository;
    
    
    /**
     * 두윗모드Task 내용 수정
     *
     * @param memberId
     * @param command
     */
    @Transactional
    public void updateDowithTaskContents(Long memberId, UpdateDowithTaskCommand command) {
        
        DowithTask dowithTask = dowithTaskRepository.getDowithTask(command.id(), memberId)
                                                    .orElseThrow(() -> new RestApiException(
                                                        DOWITH_TASK_NOT_EXIST));
        
        TaskCategory taskCategory = taskCategoryRepository.getActiveTaskCategory(
                                                              command.taskCategoryId(), memberId)
                                                          .orElseThrow(() -> new RestApiException(
                                                              DOWITH_TASK_TASK_CATEGORY_NOT_EXIST));
        
        if (!dowithTask.isContentsEditable()) {
            throw new RestApiException(DOWITH_TASK_UPDATE_NOT_AVAIL);
        }
        
        if (dowithTask.isRoutine()) {
            
            // updateAvailDates 기준으로 업데이트 대상 판별
            Map<Boolean, List<DowithTask>> updateAvailTaskMap = getUpdateAvailTaskMap(dowithTask);
            
            // 기존 routine 삭제
            dowithTaskRoutineRepository.delete(dowithTask.getRoutine());
            // 과거 Task 루틴 삭제
            updateAvailTaskMap.get(false).forEach(DowithTask::deleteRoutine);
            
            // 현재, 미래 Task 콘텐츠 + 루틴 변경
            DowithTaskRoutine newRoutine = dowithTaskRoutineRepository.save(DowithTaskRoutine.from(
                updateAvailTaskMap.get(true)
                                  .stream()
                                  .map(DowithTask::getDate)
                                  .collect(Collectors.toSet())));
            updateAvailTaskMap.get(true)
                              .forEach(task -> task.update(command.title(),
                                                           taskCategory.getId(),
                                                           command.date(),
                                                           command.startTime(),
                                                           newRoutine));
            
        } else {
            
            dowithTask.updateContent(command.title(),
                                     taskCategory.getId(),
                                     command.date(),
                                     command.startTime());
            
        }
        
    }
    
    /**
     * 두윗모드Task 루틴 수정
     *
     * @param memberId
     * @param dowithTaskId
     * @param routineDates
     */
    @Transactional
    public void updateDowithTaskRoutine(Long memberId, Long dowithTaskId,
                                        Set<LocalDate> routineDates) {
        
        DowithTask dowithTask = dowithTaskRepository.getDowithTask(dowithTaskId, memberId)
                                                    .orElseThrow(() -> new RestApiException(
                                                        DOWITH_TASK_NOT_EXIST));
        
        if (!registerAvailService.isRegisterAvail(routineDates,
                                                  dowithTaskRepository.getDowithTasks(dowithTask.getMemberId(),
                                                                                      routineDates))
                                 .isAvail()) {
            throw new RestApiException(DOWITH_TASK_CREATE_COUNT_EXCEED);
        }
        
        if (dowithTask.isRoutine()) {
            
            // updateAvailDates 기준으로 업데이트 대상 판별
            Map<Boolean, List<DowithTask>> updateAvailTaskMap = getUpdateAvailTaskMap(dowithTask);
            
            // 기존 routine 삭제
            dowithTaskRoutineRepository.delete(dowithTask.getRoutine());
            
            // 과거 Task 루틴 삭제
            updateAvailTaskMap.get(false).forEach(DowithTask::deleteRoutine);
            
            // 현재, 미래 루틴 변경
            DowithTaskRoutine newRoutine = dowithTaskRoutineRepository.save(DowithTaskRoutine.from(
                updateAvailTaskMap.get(true)
                                  .stream()
                                  .map(DowithTask::getDate)
                                  .collect(Collectors.toSet())));
            updateAvailTaskMap.get(true).forEach(task -> task.updateRoutine(newRoutine));
            
        } else {
            
            dowithTaskRoutineRepository.delete(dowithTask.getRoutine());
            dowithTask.createRoutine(routineDates);
            
        }
        
    }
    
    private Map<Boolean, List<DowithTask>> getUpdateAvailTaskMap(DowithTask dowithTask) {
        
        Set<LocalDate> updateAvailDates = dowithTask.getUpdateAvailRoutineDates();
        
        Map<Boolean, List<DowithTask>> updateAvailTaskMap = new HashMap<>();
        updateAvailTaskMap.put(true, new ArrayList<>());
        updateAvailTaskMap.put(false, new ArrayList<>());
        dowithTaskRepository.getDowithTasks(dowithTask.getRoutine())
                            .forEach(task ->
                                         updateAvailTaskMap.get(updateAvailDates.contains(task.getDate()))
                                                           .add(task)
                            );
        
        return updateAvailTaskMap;
    }
    
}
