package com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository;


import com.LetMeDoWith.LetMeDoWith.domain.member.Badge;
import java.util.List;

public interface QBadgeJpaRepository {

  List<Badge> findAllByMemberId(Long memberId);

}
