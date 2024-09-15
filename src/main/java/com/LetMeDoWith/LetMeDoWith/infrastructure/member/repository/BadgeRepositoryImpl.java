package com.LetMeDoWith.LetMeDoWith.infrastructure.member.repository;


import com.LetMeDoWith.LetMeDoWith.application.member.repository.BadgeRepository;
import com.LetMeDoWith.LetMeDoWith.domain.member.Badge;
import com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository.BadgeJpaRepository;
import com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository.MemberBadgeJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BadgeRepositoryImpl implements BadgeRepository {

  private final BadgeJpaRepository badgeJpaRepository;
  private final MemberBadgeJpaRepository memberBadgeJpaRepository;

  @Override
  public void save(List<Badge> badges) {

    badgeJpaRepository.saveAll(badges);
    badges.forEach(badge -> {
      if(badge.getMemberBadges() != null && !badge.getMemberBadges().isEmpty()) {
        memberBadgeJpaRepository.saveAll(badge.getMemberBadges());
      }
    });

  }

  public List<Badge> getBadges(Long memberId) {

    return badgeJpaRepository.findAllByMemberId(memberId);

  }

}
