package com.LetMeDoWith.LetMeDoWith.application.member.repository;

import com.LetMeDoWith.LetMeDoWith.domain.member.Member;
import com.LetMeDoWith.LetMeDoWith.domain.member.MemberAlarmSetting;

public interface MemberSettingRepository {
    
    MemberAlarmSetting saveAlarmSetting(MemberAlarmSetting memberAlarmSetting);
    
    MemberAlarmSetting findAlarmSettingByMember(Member member);
    
}