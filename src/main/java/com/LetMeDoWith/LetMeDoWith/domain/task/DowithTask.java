package com.LetMeDoWith.LetMeDoWith.domain.task;

import com.LetMeDoWith.LetMeDoWith.common.converter.YnConverter;
import com.LetMeDoWith.LetMeDoWith.common.converter.task.DowithTaskStatusConverter;
import com.LetMeDoWith.LetMeDoWith.common.entity.BaseAuditEntity;
import com.LetMeDoWith.LetMeDoWith.common.enums.common.Yn;
import com.LetMeDoWith.LetMeDoWith.common.enums.task.DowithTaskStatus;
import com.LetMeDoWith.LetMeDoWith.domain.AggregateRoot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  @OneToMany(mappedBy = "dowithTask", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<DowithTaskRoutine> routines;

  public static DowithTask ofInit(Long memberId, Long taskCategoryId, String title, LocalDateTime startDateTime) {
    return DowithTask.builder()
        .memberId(memberId)
        .taskCategoryId(taskCategoryId)
        .title(title)
        .status(DowithTaskStatus.WAIT)
        .isRoutine(Yn.FALSE)
        .startDateTime(startDateTime)
        .build();
  }

//  public void setRoutine() {
//
//  }

  public void confirm(String imageUrl) {
    confirms.add(DowithTaskConfirm.of(this, imageUrl));
    this.status = DowithTaskStatus.SUCCESS;
    this.successDateTime = LocalDateTime.now();
  }

  public void complete() {
    this.status = DowithTaskStatus.COMPLETE;
    this.completeDateTime = LocalDateTime.now();
  }





}
