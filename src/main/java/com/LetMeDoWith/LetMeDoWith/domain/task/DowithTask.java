package com.LetMeDoWith.LetMeDoWith.domain.task;

import com.LetMeDoWith.LetMeDoWith.common.converter.YnConverter;
import com.LetMeDoWith.LetMeDoWith.common.converter.task.DowithTaskStatusConverter;
import com.LetMeDoWith.LetMeDoWith.common.entity.BaseAuditEntity;
import com.LetMeDoWith.LetMeDoWith.common.enums.common.Yn;
import com.LetMeDoWith.LetMeDoWith.common.enums.task.DowithTaskStatus;
import com.LetMeDoWith.LetMeDoWith.domain.AggregateRoot;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DOWITH_TASK")
@AggregateRoot
public class DowithTask extends BaseAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "task_category_id")
  private Long taskCategoryId;

  @Column(name = "title")
  private String title;

  @Column(name = "status")
  @Convert(converter = DowithTaskStatusConverter.class)
  private DowithTaskStatus status;

  @Column(name = "routine_yn")
  @Convert(converter = YnConverter.class)
  private Yn isRoutine;

  @Column(name = "start_at")
  private LocalDateTime startDateTime;

  @Column(name = "success_at")
  private LocalDateTime successDateTime;

  @Column(name = "complete_at")
  private LocalDateTime completeDateTime;

  @OneToMany(mappedBy = "dowithTask", fetch = FetchType.LAZY)
  private List<DowithTaskConfirm> confirms;



}
