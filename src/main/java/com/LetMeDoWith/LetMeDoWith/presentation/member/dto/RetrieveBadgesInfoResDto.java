package com.LetMeDoWith.LetMeDoWith.presentation.member.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
public class RetrieveBadgesInfoResDto {
  private boolean isLazy;
  private MainBadge mainBadge;
  private List<Badge> badges;

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

  public static RetrieveBadgesInfoResDto of(boolean isLazy, @Nullable com.LetMeDoWith.LetMeDoWith.domain.member.Badge mainBadge, List<com.LetMeDoWith.LetMeDoWith.domain.member.Badge> badges) {

    List<Badge> badgesResult = new ArrayList<>();
    badges.forEach(e -> badgesResult.add(Badge.builder()
        .id(e.getId())
        .isActive(e.isActive())
        .name(e.getName())
        .imageUrl(e.getImage())
        .description(e.getDescription())
        .activeHint(e.getActiveHint())
        .build()));

    return RetrieveBadgesInfoResDto.builder()
        .isLazy(isLazy)
        .mainBadge(mainBadge == null ? null : MainBadge.builder()
            .id(mainBadge.getId())
            .name(mainBadge.getName())
            .imageUrl(mainBadge.getImage())
            .description(mainBadge.getDescription())
            .build())
        .badges(badgesResult)
        .build();
  }
}
