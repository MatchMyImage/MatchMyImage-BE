package com.LetMeDoWith.LetMeDoWith.domain.task.model;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DowithTaskRoutineDates {
  private Set<LocalDate> dates;

  public boolean isEqual(Set<LocalDate> dates) {
    return this.dates.equals(dates);
  }
}
