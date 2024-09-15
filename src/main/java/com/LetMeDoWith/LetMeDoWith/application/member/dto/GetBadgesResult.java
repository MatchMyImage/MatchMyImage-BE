package com.LetMeDoWith.LetMeDoWith.application.member.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class GetBadgesResult {
  private boolean isLazy;

  @Data
  @Builder
  public static class MainBadge {
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
  }

  @Data
  @Builder
  public static class Badge {
    private Long id;
    private boolean isActive;
    private String name;
    private String imageUrl;
    private String description;
    private String activeHint;
  }

  public static GetBadgesResult of(boolean isLazy, Badge mainBadge, List<Badge> badges) {

  }
}
