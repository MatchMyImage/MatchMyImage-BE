package com.LetMeDoWith.LetMeDoWith.controller.member;

import com.LetMeDoWith.LetMeDoWith.dto.requestDto.CreateMemberTermAgreeReq;
import com.LetMeDoWith.LetMeDoWith.dto.requestDto.SignupCompleteReq;
import com.LetMeDoWith.LetMeDoWith.dto.responseDto.CreateTokenResDto;
import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.enums.common.SuccessResponseStatus;
import com.LetMeDoWith.LetMeDoWith.service.AuthService;
import com.LetMeDoWith.LetMeDoWith.service.Member.MemberService;
import com.LetMeDoWith.LetMeDoWith.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;
    private final AuthService authService;
    
    /**
     * 회원가입을 완료한다. 회원가입이 완료되면 로그인한다.
     *
     * @param signupCompleteReq 회원가입을 완료하려는 멤버의 추가 정보
     * @return 로그인 결과. 액세스 트큰과, refresh 토큰
     */
    @PostMapping("")
    public ResponseEntity completeSignup(SignupCompleteReq signupCompleteReq) {
        
        Member signupCompletedMember = memberService.createSignupCompletedMember(signupCompleteReq);
        CreateTokenResDto token = authService.login(signupCompletedMember);
        
        return ResponseUtil.createSuccessResponse(SuccessResponseStatus.LOGIN_SUCCESS, token);
    }
    
    /**
     * 멤버의 약관 동의 사항을 생성한다.
     *
     * @param memberId                 약관동의를 생성할 멤버
     * @param createMemberTermAgreeReq 멤버의 약관 동의 사항. 필수 동의사항은 false일 수 없다.
     * @return 성공 메세지
     */
    @PostMapping("/{memberId}/agreement")
    public ResponseEntity createMemberTermAgree(
        @PathVariable Long memberId,
        @RequestBody CreateMemberTermAgreeReq createMemberTermAgreeReq) {
        
        memberService.createMemberTermAgree(createMemberTermAgreeReq);
        
        return ResponseUtil.createSuccessResponse(SuccessResponseStatus.OK_WITHOUT_DATA);
    }
}