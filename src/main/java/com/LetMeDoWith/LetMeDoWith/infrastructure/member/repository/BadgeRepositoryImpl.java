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

  public List<Badge> getBadges(Long memberId) {

    return badgeJpaRepository.findAllByMemberId(memberId);

  }

}
