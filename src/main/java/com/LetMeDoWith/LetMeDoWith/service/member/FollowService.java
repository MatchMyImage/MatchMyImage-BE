package com.LetMeDoWith.LetMeDoWith.service.member;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.LetMeDoWith.LetMeDoWith.dto.responseDto.RetrieveFollowsResDto;
import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.entity.member.MemberFollow;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.FollowType;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberFollowRepository;
import com.LetMeDoWith.LetMeDoWith.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {

	private final MemberFollowRepository memberFollowRepository;
	private final MemberRepository memberRepository;

	public RetrieveFollowsResDto retrieveFollows(Long memberId, FollowType followType, Pageable pageable) {

		Member member = memberRepository.findByIdAndStatus(memberId, MemberStatus.NORMAL)
			.orElseThrow(() -> new RestApiException(FailResponseStatus.INVALID_FOLLOWER_MEMBER));

		RetrieveFollowsResDto result = null;
		switch (followType) {
			case FOLLOWER -> {
				List<MemberFollow> memberFollows = memberFollowRepository.findAllFollowersByFollowingMemberFetchJoinMember(
					member, pageable);
				result = RetrieveFollowsResDto.builder().follows(memberFollows.stream()
					.map(e -> new RetrieveFollowsResDto.Follow(
						e.getFollowerMember().getId(),
						e.getFollowerMember().getNickname(),
						e.getFollowerMember().getSelfDescription(),
						e.getFollowerMember().getProfileImageUrl()
					))
					.toList()).build();
			}
			case FOLLOWING -> {
				List<MemberFollow> memberFollows = memberFollowRepository.findAllFollowingsByFollowerMemberFetchJoinMember(member, pageable);
				result = RetrieveFollowsResDto.builder().follows(memberFollows.stream()
					.map(e -> new RetrieveFollowsResDto.Follow(
						e.getFollowingMember().getId(),
						e.getFollowingMember().getNickname(),
						e.getFollowingMember().getSelfDescription(),
						e.getFollowingMember().getProfileImageUrl()
					)).toList()).build();
			}
		}

		return result;
	}

	@Transactional
	public void createFollow(Long memberId, Long followingMemberId) {

		Member followerMember = memberRepository.findByIdAndStatus(memberId, MemberStatus.NORMAL)
			.orElseThrow(() -> new RestApiException(FailResponseStatus.INVALID_FOLLOWER_MEMBER));
		Member followingMember = memberRepository.findByIdAndStatus(followingMemberId, MemberStatus.NORMAL)
			.orElseThrow(() -> new RestApiException(
				FailResponseStatus.INVALID_FOLLOWING_MEMBER));

		MemberFollow memberFollow = memberFollowRepository.save(MemberFollow.builder()
			.followerMember(followerMember)
			.followingMember(followingMember)
			.build());

	}

	@Transactional
	public void deleteFollow(Long memberId, Long followingMemberId) {

		MemberFollow memberFollow = memberFollowRepository.findAllByFollowerMemberIdAndFollowingMemberId(memberId,
			followingMemberId).orElseThrow(() -> new RestApiException(FailResponseStatus.MEMBER_FOLLOW_NOT_EXIST));

		memberFollowRepository.delete(memberFollow);

	}


}
