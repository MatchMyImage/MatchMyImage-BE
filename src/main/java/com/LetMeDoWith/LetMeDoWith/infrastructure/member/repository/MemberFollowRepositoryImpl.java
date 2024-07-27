package com.LetMeDoWith.LetMeDoWith.infrastructure.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.LetMeDoWith.LetMeDoWith.application.member.domain.Member;
import com.LetMeDoWith.LetMeDoWith.application.member.domain.MemberFollow;
import com.LetMeDoWith.LetMeDoWith.application.member.repository.MemberFollowRepository;
import com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository.MemberFollowJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberFollowRepositoryImpl implements MemberFollowRepository {

	private final MemberFollowJpaRepository memberFollowJpaRepository;

	@Override
	public MemberFollow save(Member followerMember, Member followingMember) {

		return memberFollowJpaRepository.save(MemberFollow.builder()
			.followerMember(
				followerMember)
			.followingMember(
				followingMember)
			.build());

	}

	@Override
	public List<MemberFollow> getFollowers(Member followingMember, Pageable pageable) {

		return memberFollowJpaRepository.findAllFollowersByFollowingMemberFetchJoinMember(
			followingMember, pageable);

	}

	@Override
	public List<MemberFollow> getFollowings(Member followerMember, Pageable pageable) {

		return memberFollowJpaRepository.findAllFollowingsByFollowerMemberFetchJoinMember(
			followerMember,
			pageable);

	}

	@Override
	public Optional<MemberFollow> getFollowing(Long memberId, Long followingMemberId) {
		return memberFollowJpaRepository.findByFollowerMemberIdAndFollowingMemberId(
				memberId,
				followingMemberId);
	}

	@Override
	public void delete(MemberFollow memberFollow) {
		memberFollowJpaRepository.delete(memberFollow);
	}
}
