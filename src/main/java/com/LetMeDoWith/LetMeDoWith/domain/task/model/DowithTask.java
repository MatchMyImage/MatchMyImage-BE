package com.LetMeDoWith.LetMeDoWith.domain.task.model;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_ROUTINE_NOT_EXIST;

import com.LetMeDoWith.LetMeDoWith.common.entity.BaseAuditEntity;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.domain.AggregateRoot;
import com.LetMeDoWith.LetMeDoWith.domain.task.enums.DowithTaskStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "dowith_task")
@AggregateRoot
public class DowithTask extends BaseAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Column(name = "task_category_id", nullable = true)
  private Long taskCategoryId;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "status", nullable = false)
  private DowithTaskStatus status;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "start_time", nullable = true)
  private LocalTime startTime;

  @Column(name = "success_at")
  private LocalDateTime successDateTime;

  @Column(name = "complete_at")
  private LocalDateTime completeDateTime;

  @OneToOne(mappedBy = "dowithTask", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private DowithTaskConfirm confirms;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "dowith_task_routine_id")
  private DowithTaskRoutine routine;


  public static DowithTask of(Long memberId, Long taskCategoryId, String title, LocalDate date,
      LocalTime startTime) {
    DowithTask newDowithTask = DowithTask.builder()
        .memberId(memberId)
        .taskCategoryId(taskCategoryId)
        .title(title)
        .status(DowithTaskStatus.WAIT)
        .date(date)
        .startTime(startTime)
        .routine(null)
        .confirms(null)
        .build();
    newDowithTask.validate();
    return newDowithTask;
  }

  public static List<DowithTask> ofWithRoutine(Long memberId, Long taskCategoryId, String title,
      LocalDate date, LocalTime startTime, Set<LocalDate> routineDates) {
    List<DowithTask> result = new ArrayList<>();
    Set<LocalDate> targetDateSet = new TreeSet<>(routineDates);
    targetDateSet.add(date);

    DowithTaskRoutine routine = DowithTaskRoutine.from(targetDateSet);
    targetDateSet.stream().sorted().toList().forEach(e -> {
      DowithTask newDowithTask = DowithTask.builder()
          .memberId(memberId)
          .taskCategoryId(taskCategoryId)
          .title(title)
          .status(DowithTaskStatus.WAIT)
          .routine(routine)
          .date(e)
          .startTime(startTime)
          .build();
      newDowithTask.validate();
      result.add(newDowithTask);
    });

    return result;
  }

  public static void updateWithRoutines(List<DowithTask> existings, String title,
      Long taskCategoryId, LocalDate date,
      LocalTime startTime, Set<LocalDate> routineDates) {

    Set<LocalDate> targetDateSet = new TreeSet<>(routineDates);
    targetDateSet.add(date);

    DowithTaskRoutine routine = DowithTaskRoutine.from(targetDateSet);

    // targetDateSet에 없는 date들은 삭제

    // targetDateSet에는 있는데, existings에는 없는 애들은 생성

    existings.forEach(task -> task.update(title, taskCategoryId, date, startTime));

    if (!isRoutine()) {
      throw new RestApiException(DOWITH_TASK_ROUTINE_NOT_EXIST);
    }

    this.routine.updateRoutineDates();

  }

  public void update(String title, Long taskCategoryId, LocalDate date, LocalTime startTime) {

    this.title = title;
    this.taskCategoryId = taskCategoryId;
    this.date = date;
    this.startTime = startTime;

    if (isRoutine()) {
      routine = null;
    }

    validate();

  }

  private void validate() {
    if (LocalDate.now().isEqual(date)) {
      if (startTime.isAfter(LocalTime.now())) {
        throw new RestApiException(FailResponseStatus.DOWITH_TASK_NOT_AVAIL_START_TIME);
      }
    }
    if (date.isBefore(LocalDate.now())) {
      throw new RestApiException(FailResponseStatus.DOWITH_TASK_NOT_AVAIL_DATE);
    }
    if (isRoutine()) {
      routine.getRoutineDates().validate();
    }
  }

  public boolean isRoutine() {
    return routine != null;
  }

  public Set<LocalDate> getRoutineDates() {
    if (isRoutine()) {
      return this.routine.getRoutineDates().getDates();
    } else {
      return Set.of();
    }
  }

  public Long getRoutineId() {
    return isRoutine() ? routine.getId() : null;
  }

  public void confirm(String imageUrl) {
    confirms = DowithTaskConfirm.of(this, imageUrl);
    this.status = DowithTaskStatus.SUCCESS;
    this.successDateTime = LocalDateTime.now();
  }

//  public boolean isEqual(LocalDate date, LocalTime startTime, Set<LocalDate> routineDates) {
//    if(!this.date.equals(date)) return false;
//    if(!this.startTime.equals(startTime)) return false;
//    if(isRoutine()) {
//      return this.routine.isEqual(routineDates);
//    }else {
//      return routineDates == null || routineDates.isEmpty();
//    }
//  }

  public void complete() {
    this.status = DowithTaskStatus.COMPLETE;
    this.completeDateTime = LocalDateTime.now();
  }

}
