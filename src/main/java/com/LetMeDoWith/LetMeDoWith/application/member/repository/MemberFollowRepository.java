package com.LetMeDoWith.LetMeDoWith.application.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.LetMeDoWith.LetMeDoWith.application.member.domain.Member;
import com.LetMeDoWith.LetMeDoWith.application.member.domain.MemberFollow;

public interface MemberFollowRepository {

	MemberFollow save(Member followerMember, Member followingMember);

	List<MemberFollow> getFollowers(Member followingMember, Pageable pageable);
	List<MemberFollow> getFollowings(Member followerMember, Pageable pageable);
	Optional<MemberFollow> getFollowing(Long memberId, Long followingMemberId);

	void delete(MemberFollow memberFollow);

}
