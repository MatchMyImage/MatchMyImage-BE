package com.LetMeDoWith.LetMeDoWith.domain.task.model;

import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus;
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

  public void validate() {
    dates.forEach(date -> {
      if(date.isBefore(LocalDate.now())) throw new RestApiException(FailResponseStatus.DOWITH_TASK_NOT_AVAIL_DATE);
    });
  }

}
