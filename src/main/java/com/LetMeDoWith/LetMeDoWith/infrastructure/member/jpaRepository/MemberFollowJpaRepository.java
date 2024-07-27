package com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LetMeDoWith.LetMeDoWith.application.member.domain.MemberFollow;

@Repository
public interface MemberFollowJpaRepository extends JpaRepository<MemberFollow, Long>, QMemberFollowJpaRepository {

	Optional<MemberFollow> findByFollowerMemberIdAndFollowingMemberId(Long followerMemberId, Long followingMemberId);

}
