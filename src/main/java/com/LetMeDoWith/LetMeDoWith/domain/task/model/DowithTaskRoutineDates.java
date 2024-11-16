package com.LetMeDoWith.LetMeDoWith.domain.task.model;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DowithTaskRoutineDates {

  private Set<LocalDate> dates;

  public static DowithTaskRoutineDates from(Set<LocalDate> dates) {
    return new DowithTaskRoutineDates(dates);
  }

}
