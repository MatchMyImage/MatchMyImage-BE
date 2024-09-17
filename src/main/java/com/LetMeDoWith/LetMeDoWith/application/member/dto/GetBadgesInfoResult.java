package com.LetMeDoWith.LetMeDoWith.application.member.dto;

import com.LetMeDoWith.LetMeDoWith.domain.member.Badge;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetBadgesInfoResult {

  private boolean isLazy;
  private List<MemberBadgeVO> badges;

  public static GetBadgesInfoResult of(boolean isLazy, List<MemberBadgeVO> badges) {
    return GetBadgesInfoResult.builder()
        .isLazy(isLazy)
        .badges(badges)
        .build();
  }

}
