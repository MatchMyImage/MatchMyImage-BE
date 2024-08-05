package com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.LetMeDoWith.LetMeDoWith.domain.member.Member;
import com.LetMeDoWith.LetMeDoWith.domain.member.MemberFollow;

public interface QMemberFollowJpaRepository {

	List<MemberFollow> findAllFollowingsByFollowerMemberFetchJoinMember(Member followerMember, Pageable pageable);
	List<MemberFollow> findAllFollowersByFollowingMemberFetchJoinMember(Member followingMember, Pageable pageable);
}
