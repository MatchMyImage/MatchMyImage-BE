package com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.LetMeDoWith.LetMeDoWith.application.member.domain.Member;
import com.LetMeDoWith.LetMeDoWith.application.member.domain.MemberFollow;

public interface QMemberFollowJpaRepository {

	List<MemberFollow> findAllFollowingsByFollowerMemberFetchJoinMember(Member followerMember, Pageable pageable);
	List<MemberFollow> findAllFollowersByFollowingMemberFetchJoinMember(Member followingMember, Pageable pageable);
}
