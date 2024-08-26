package com.LetMeDoWith.LetMeDoWith.presentation.member.dto;

public record UpdateMemberAlarmSettingReq(
    boolean baseAlarmYn,
    boolean todoBotYn,
    boolean feedbackYn,
    boolean marketingYn
) {

}