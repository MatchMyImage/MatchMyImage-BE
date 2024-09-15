package com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository;

import com.LetMeDoWith.LetMeDoWith.domain.member.Badge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeJpaRepository extends JpaRepository<Badge, Long>, QBadgeJpaRepository {

}
