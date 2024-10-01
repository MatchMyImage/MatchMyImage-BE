package com.LetMeDoWith.LetMeDoWith.presentation.member.controller;

import com.LetMeDoWith.LetMeDoWith.presentation.member.dto.CreateFollowReqDto;
import com.LetMeDoWith.LetMeDoWith.presentation.member.dto.RetrieveFollowsResDto;
import com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.FollowType;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.application.member.service.FollowService;
import com.LetMeDoWith.LetMeDoWith.common.util.AuthUtil;
import com.LetMeDoWith.LetMeDoWith.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/v1/member/follow")
@RequiredArgsConstructor
public class FollowController {
    
    private final FollowService followService;
    
    @GetMapping("/{memberId}")
    public ResponseEntity retrieveFollows(@PathVariable(name = "memberId") Long memberId,
        @RequestParam(name = "followType") FollowType type,
        Pageable pageable) {
        
        Long tokenMemberId = AuthUtil.getMemberId();
        if (!tokenMemberId.equals(memberId)) {
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