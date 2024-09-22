package com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository;

import com.LetMeDoWith.LetMeDoWith.domain.member.Member;
import com.LetMeDoWith.LetMeDoWith.domain.member.MemberAlarmSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAlarmSettingJpaRepository extends JpaRepository<MemberAlarmSetting, Long> {
    
    MemberAlarmSetting findByMember(Member member);
    
    
}