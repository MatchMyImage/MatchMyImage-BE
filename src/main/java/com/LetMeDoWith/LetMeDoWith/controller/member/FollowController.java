package com.LetMeDoWith.LetMeDoWith.controller.member;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LetMeDoWith.LetMeDoWith.dto.requestDto.CreateFollowReqDto;
import com.LetMeDoWith.LetMeDoWith.dto.responseDto.RetrieveFollowsResDto;
import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.FollowType;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.service.member.FollowService;
import com.LetMeDoWith.LetMeDoWith.util.AuthUtil;
import com.LetMeDoWith.LetMeDoWith.util.ResponseUtil;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/member/follow")
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;

	@GetMapping("/{memberId}")
	public ResponseEntity retrieveFollows(@PathVariable(name = "memberId") Long memberId, @RequestParam(name = "followType") FollowType type, Pageable pageable) {

		Long tokenMemberId = AuthUtil.getMemberId();
		if(!tokenMemberId.equals(memberId)) {
			throw new RestApiException(FailResponseStatus.UNAUTHORIZED);
		}

		RetrieveFollowsResDto result = followService.retrieveFollows(memberId, type, pageable);

		return ResponseUtil.createSuccessResponse(result, pageable);
	}

	@PostMapping()
	public ResponseEntity createFollow(@RequestBody CreateFollowReqDto requestBody) {

		followService.createFollow(AuthUtil.getMemberId(), requestBody.followMemberId());

		return ResponseUtil.createSuccessResponse();
	}

	@DeleteMapping("/{followingId}")
	public ResponseEntity deleteFollow(@PathVariable Long followingId) {

		followService.deleteFollow(AuthUtil.getMemberId(), followingId);

		return ResponseUtil.createSuccessResponse();
	}
}
