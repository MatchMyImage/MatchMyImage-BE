package com.LetMeDoWith.LetMeDoWith.domain.task.model;

import com.LetMeDoWith.LetMeDoWith.common.entity.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Set;
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
@Table(name = "DOWITH_TASK_CONFIRM")
public class DowithTaskRoutine extends BaseAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Embedded
  @Column(name = "rountine_dates")
  private DowithTaskRoutineDates routineDates;

  public static DowithTaskRoutine create(Set<LocalDate> dates) {
    return DowithTaskRoutine.builder()
        .routineDates(new DowithTaskRoutineDates(dates))
        .build();
  }

  public boolean isEqual(Set<LocalDate> routineDates) {
    return this.routineDates.isEqual(routineDates);
  }

}
