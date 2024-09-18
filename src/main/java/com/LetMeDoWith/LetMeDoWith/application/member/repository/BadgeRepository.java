package com.LetMeDoWith.LetMeDoWith.application.member.repository;

import com.LetMeDoWith.LetMeDoWith.application.member.dto.MemberBadgeVO;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.BadgeStatus;
import com.LetMeDoWith.LetMeDoWith.domain.member.Badge;
import com.LetMeDoWith.LetMeDoWith.domain.member.MemberBadge;
import java.util.List;
import java.util.Optional;

public interface BadgeRepository {

  void save(List<Badge> badges);
  void save(MemberBadge memberBadge);
  Optional<Badge> getBadge(Long badgeId, BadgeStatus badgeStatus);

  Optional<MemberBadge> getMemberBadge(Long memberId, Badge badge);
  Optional<MemberBadge> getMainMemberBadge(Long memberId);
  List<MemberBadgeVO> getBadges(Long memberId);

}
