package com.LetMeDoWith.LetMeDoWith.application.member.repository;

import com.LetMeDoWith.LetMeDoWith.application.member.dto.MemberBadgeVO;
import com.LetMeDoWith.LetMeDoWith.domain.member.Badge;
import java.util.List;

public interface BadgeRepository {

  void save(List<Badge> badges);
  List<MemberBadgeVO> getBadges(Long memberId);

}
