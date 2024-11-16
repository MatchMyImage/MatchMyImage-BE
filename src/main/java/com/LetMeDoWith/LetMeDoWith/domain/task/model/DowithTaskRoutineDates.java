package com.LetMeDoWith.LetMeDoWith.domain.task.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.time.LocalDate;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DowithTaskRoutineDate {

  private Set<LocalDate> dates;

  public static DowithTaskRoutineDate of(Set<LocalDate> dates) {
    return new DowithTaskRoutineDate(dates);
  }

}
