package com.LetMeDoWith.LetMeDoWith.controller.member;

import com.LetMeDoWith.LetMeDoWith.dto.requestDto.SignupCompleteReq;
import com.LetMeDoWith.LetMeDoWith.dto.responseDto.CreateTokenResDto;
import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.enums.common.SuccessResponseStatus;
import com.LetMeDoWith.LetMeDoWith.service.AuthService;
import com.LetMeDoWith.LetMeDoWith.service.Member.MemberService;
import com.LetMeDoWith.LetMeDoWith.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;
    private final AuthService authService;
    
    @PostMapping("")
    public ResponseEntity completeSignup(SignupCompleteReq signupCompleteReq) {
        
        Member signupCompletedMember = memberService.createSignupCompletedMember(signupCompleteReq);
        CreateTokenResDto token = authService.login(signupCompletedMember);
        
        return ResponseUtil.createSuccessResponse(SuccessResponseStatus.LOGIN_SUCCESS, token);
    }
}