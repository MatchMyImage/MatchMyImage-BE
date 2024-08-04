package com.LetMeDoWith.LetMeDoWith.domain.member;

import com.LetMeDoWith.LetMeDoWith.common.entity.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member_push_setting")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberPushSetting extends BaseAuditEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_push_setting_id", nullable = false)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
    @Column(nullable = false)
    @Builder.Default
    private boolean basePushYn = true;
    
    @Column(nullable = false)
    @Builder.Default
    private boolean todoBotYn = true;
    
    @Column(nullable = false)
    @Builder.Default
    private boolean feedbackYn = true;
    
    @Column(nullable = false)
    @Builder.Default
    private boolean marketingYn = true;
}