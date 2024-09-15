package com.LetMeDoWith.LetMeDoWith.application.member.dto;

import com.LetMeDoWith.LetMeDoWith.domain.member.Badge;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetBadgesInfoResult {

  private boolean isLazy;
  public List<Badge> badges;

  public static GetBadgesInfoResult of(boolean isLazy, List<Badge> badges) {
    return GetBadgesInfoResult.builder()
        .isLazy(isLazy)
        .badges(badges)
        .build();
  }

}
