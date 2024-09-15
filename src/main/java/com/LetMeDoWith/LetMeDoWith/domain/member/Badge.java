package com.LetMeDoWith.LetMeDoWith.domain.member;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.MEMBER_BADGE_NOT_EXIST;

import com.LetMeDoWith.LetMeDoWith.common.entity.BaseAuditEntity;
import com.LetMeDoWith.LetMeDoWith.common.enums.common.Yn;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.BadgeStatus;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "BADGE")
public class Badge extends BaseAuditEntity {

  @OneToMany(mappedBy = "badge", fetch = FetchType.LAZY)
  protected List<MemberBadge> memberBadges;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "status", nullable = false)
  private BadgeStatus badgeStatus;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "active_hint")
  private String activeHint;

  @Column(name = "image_url")
  private String image;

  @Column(name = "sort_order")
  private int sortOrder;

  public boolean isActive() {
    return memberBadges != null && !memberBadges.isEmpty();
  }

  public boolean isMainBadge() {
    if(memberBadges == null || memberBadges.isEmpty()) return false;
    MemberBadge memberBadge = this.memberBadges.get(0);
    return Yn.TRUE.equals(memberBadge.getIsMain());
  }

  public void registerToMain() {
    if(memberBadges ==null || memberBadges.isEmpty()) throw new RestApiException(MEMBER_BADGE_NOT_EXIST);
    MemberBadge memberBadge = this.memberBadges.get(0);
    memberBadge.registerToMainBadge();
  }

  public void cancelMain() {
    if(memberBadges ==null || memberBadges.isEmpty()) throw new RestApiException(MEMBER_BADGE_NOT_EXIST);
    MemberBadge memberBadge = this.memberBadges.get(0);
    memberBadge.cancelMainBadge();
  }



}
