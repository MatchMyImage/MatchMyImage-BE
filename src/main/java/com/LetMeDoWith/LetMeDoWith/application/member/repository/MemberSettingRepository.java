package com.LetMeDoWith.LetMeDoWith.application.member.repository;

import com.LetMeDoWith.LetMeDoWith.domain.member.model.Member;
import com.LetMeDoWith.LetMeDoWith.domain.member.model.MemberAlarmSetting;

public interface MemberSettingRepository {
    
    MemberAlarmSetting save(MemberAlarmSetting memberAlarmSetting);
    
    MemberAlarmSetting findAlarmSettingByMember(Member member);
    
}