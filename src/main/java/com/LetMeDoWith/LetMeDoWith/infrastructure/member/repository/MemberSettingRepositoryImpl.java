package com.LetMeDoWith.LetMeDoWith.infrastructure.member.repository;

import com.LetMeDoWith.LetMeDoWith.application.member.repository.MemberSettingRepository;
import com.LetMeDoWith.LetMeDoWith.domain.member.Member;
import com.LetMeDoWith.LetMeDoWith.domain.member.MemberAlarmSetting;
import com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository.MemberAlarmSettingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberSettingRepositoryImpl implements MemberSettingRepository {
    
    private final MemberAlarmSettingJpaRepository memberAlarmSettingJpaRepository;
    
    @Override
    public MemberAlarmSetting save(MemberAlarmSetting memberAlarmSetting) {
        return memberAlarmSettingJpaRepository.save(memberAlarmSetting);
    }
    
    @Override
    public MemberAlarmSetting findAlarmSettingByMember(Member member) {
        return memberAlarmSettingJpaRepository.findByMember(member);
    }
    
}