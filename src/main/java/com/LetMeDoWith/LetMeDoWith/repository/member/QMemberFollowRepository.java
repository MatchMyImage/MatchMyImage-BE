package com.LetMeDoWith.LetMeDoWith.repository.member;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberFollow;

public interface QMemberFollowRepository {

	List<MemberFollow> findAllFollowingsByFollowerMemberFetchJoinMember(Member followerMember, Pageable pageable);
	List<MemberFollow> findAllFollowersByFollowingMemberFetchJoinMember(Member followingMember, Pageable pageable);
}
