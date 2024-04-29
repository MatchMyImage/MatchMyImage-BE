package com.LetMeDoWith.LetMeDoWith.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LetMeDoWith.LetMeDoWith.entity.member.MemberFollow;

@Repository
public interface MemberFollowRepository extends JpaRepository<MemberFollow, Long>, QMemberFollowRepository {

	Optional<MemberFollow> findAllByFollowerMemberIdAndFollowingMemberId(Long followerMemberId, Long followingMemberId);

}
