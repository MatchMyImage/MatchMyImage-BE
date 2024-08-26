package com.LetMeDoWith.LetMeDoWith.presentation.member.controller;

import com.LetMeDoWith.LetMeDoWith.application.member.dto.UpdateMemberAlarmSettingCommand;
import com.LetMeDoWith.LetMeDoWith.application.member.service.MemberSettingService;
import com.LetMeDoWith.LetMeDoWith.common.util.ResponseUtil;
import com.LetMeDoWith.LetMeDoWith.presentation.member.dto.UpdateMemberAlarmSettingReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member/setting")
@RequiredArgsConstructor
public class MemberSettingController {
    
    private final MemberSettingService memberSettingService;
    
    @PutMapping("/alarm")
    public ResponseEntity updateAlarm(@RequestBody UpdateMemberAlarmSettingReq req) {
        memberSettingService.updateAlarmSetting(UpdateMemberAlarmSettingCommand.fromReq(req));
        
        return ResponseUtil.createSuccessResponse();
    }
    
}