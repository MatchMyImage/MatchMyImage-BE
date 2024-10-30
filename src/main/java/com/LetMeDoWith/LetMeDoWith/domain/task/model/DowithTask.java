package com.LetMeDoWith.LetMeDoWith.domain.task.model;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.DOWITH_TASK_UPDATE_NOT_AVAIL;

import com.LetMeDoWith.LetMeDoWith.application.task.dto.DowithTaskRoutineInfoVO;
import com.LetMeDoWith.LetMeDoWith.common.converter.YnConverter;
import com.LetMeDoWith.LetMeDoWith.common.converter.task.DowithTaskStatusConverter;
import com.LetMeDoWith.LetMeDoWith.common.entity.BaseAuditEntity;
import com.LetMeDoWith.LetMeDoWith.common.enums.common.Yn;
import com.LetMeDoWith.LetMeDoWith.common.enums.task.DowithTaskStatus;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.common.util.EnumUtil;
import com.LetMeDoWith.LetMeDoWith.domain.AggregateRoot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "DOWITH_TASK")
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
  @Convert(converter = DowithTaskStatusConverter.class)
  private DowithTaskStatus status;

  @Column(name = "routine_yn", nullable = false)
  @Convert(converter = YnConverter.class)
  private Yn isRoutine;

  @Column(name = "start_at",nullable = false)
  private LocalDateTime startDateTime;

  @Column(name = "success_at")
  private LocalDateTime successDateTime;

  @Column(name = "complete_at")
  private LocalDateTime completeDateTime;

  @OneToMany(mappedBy = "dowithTask", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<DowithTaskConfirm> confirms;

  @ManyToOne(cascade = CascadeType.ALL)
  @Column(name = "dowith_task_routine_id")
  private DowithTaskRoutine routine;

  public static DowithTask create(Long memberId, Long taskCategoryId, String title, LocalDateTime startDateTime) {
    return DowithTask.builder()
        .memberId(memberId)
        .taskCategoryId(taskCategoryId)
        .title(title)
        .status(DowithTaskStatus.WAIT)
        .isRoutine(Yn.FALSE)
        .startDateTime(startDateTime)
        .build();
  }

  public static List<DowithTask> createWithRoutine(Long memberId, Long taskCategoryId, String title, LocalDateTime startDateTime, List<LocalDate> routineDates) {
    return List.of(DowithTask.builder()
        .memberId(memberId)
        .taskCategoryId(taskCategoryId)
        .title(title)
        .status(DowithTaskStatus.WAIT)
        .isRoutine(Yn.TRUE)
        .startDateTime(startDateTime)
        .build()); // TODO - 정책 수립 시 수정 필요
  }

  public static boolean validateRegisterAvailable(List<DowithTask> existings, LocalDate targetDate) {
    Map<LocalDate, List<DowithTask>> dowithTaskMap = existings.stream()
        .collect(Collectors.groupingBy(e -> e.getStartDateTime().toLocalDate()));

    return !dowithTaskMap.containsKey(targetDate);
  }

  public static boolean validateRegisterAvailable(List<DowithTask> existings, List<LocalDate> targetDates) {
    Map<LocalDate, List<DowithTask>> dowithTaskMap = existings.stream()
        .collect(Collectors.groupingBy(e -> e.getStartDateTime().toLocalDate()));

    List<LocalDate> notAvailableDates = new ArrayList<>();
    targetDates.forEach(date -> {
      if(dowithTaskMap.containsKey(date)) notAvailableDates.add(date);
    });

    return notAvailableDates.isEmpty();
  }



  public void confirm(String imageUrl) {
    confirms.add(DowithTaskConfirm.of(this, imageUrl));
    this.status = DowithTaskStatus.SUCCESS;
    this.successDateTime = LocalDateTime.now();
  }

  public void complete() {
    this.status = DowithTaskStatus.COMPLETE;
    this.completeDateTime = LocalDateTime.now();
  }

  public void update(String title, @Nullable Long taskCategoryId, @Nullable LocalDateTime startDateTime, @Nullable Boolean isRoutine, @Nullable DowithTaskRoutineInfoVO routineInfo) {

    if(LocalDateTime.now().isBefore(this.startDateTime)) {
      this.title = title;
      this.taskCategoryId = taskCategoryId;
      this.startDateTime = startDateTime;
      this.isRoutine = EnumUtil.getEnum(Yn.class, Boolean.TRUE.equals(isRoutine) ? "Y" : "N");

    }else {
      if(taskCategoryId != null && startDateTime != null && isRoutine != null && routineInfo != null) {
        throw new RestApiException(DOWITH_TASK_UPDATE_NOT_AVAIL);
      }
      this.title = title;
    }

  }





}
