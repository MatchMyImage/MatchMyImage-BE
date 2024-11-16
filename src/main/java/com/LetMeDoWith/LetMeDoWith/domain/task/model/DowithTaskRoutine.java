package com.LetMeDoWith.LetMeDoWith.domain.task.model;

import com.LetMeDoWith.LetMeDoWith.common.entity.BaseAuditEntity;
import com.LetMeDoWith.LetMeDoWith.domain.converter.DowithTaskRoutineDatesConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
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
@Table(name = "dowith_task_routine")
public class DowithTaskRoutine extends BaseAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "dates", columnDefinition = "TEXT")
  @Convert(converter = DowithTaskRoutineDatesConverter.class)
  private DowithTaskRoutineDates routineDates;

  public static DowithTaskRoutine from(Set<LocalDate> dates) {
    return DowithTaskRoutine.builder()
        .routineDates(DowithTaskRoutineDates.from(dates))
        .build();
  }


//  @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  private List<DowithTaskRoutineDate2> routineDates;

//  public static DowithTaskRoutine from(Set<LocalDate> dates) {
//    DowithTaskRoutine dowithTaskRoutine = new DowithTaskRoutine();
//    List<DowithTaskRoutineDate2> dowithTaskRoutineDates = dates.stream()
//        .map(date -> DowithTaskRoutineDate2.of(dowithTaskRoutine, date))
//        .collect(Collectors.toList());
//    dowithTaskRoutine.updateRoutineDates(dowithTaskRoutineDates);
//    return dowithTaskRoutine;
//  }
//
//  private void updateRoutineDates(List<DowithTaskRoutineDate2> routineDates) {
//    this.routineDates = routineDates;
//  }

//  public boolean isEqual(Set<LocalDate> routineDates) {
//    return this.routineDates.isEqual(routineDates);
//  }

}
