package com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository;

import com.LetMeDoWith.LetMeDoWith.domain.member.Badge;
import com.LetMeDoWith.LetMeDoWith.domain.member.QBadge;
import com.LetMeDoWith.LetMeDoWith.domain.member.QMemberBadge;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QBadgeJpaRepositoryImpl implements QBadgeJpaRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private QBadge qBadge = QBadge.badge;
  private QMemberBadge qMemberBadge = QMemberBadge.memberBadge;

  @Override
  public List<Badge> findAllByMemberId(Long memberId) {
    return jpaQueryFactory.select(qBadge)
        .from(qMemberBadge)
        .innerJoin(qMemberBadge.badge, qBadge)
        .where(qMemberBadge.memberId.eq(memberId))
        .orderBy(qBadge.sortOrder.asc())
        .fetch();
  }
}
