package com.LetMeDoWith.LetMeDoWith.model.user;

import com.LetMeDoWith.LetMeDoWith.model.BaseAuditModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user_push_setting")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPushSetting extends BaseAuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_push_setting_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean basePushYn = true;

    @Column(nullable = false)
    private boolean todoBotYn = true;

    @Column(nullable = false)
    private boolean feedbackYn = true;

    @Column(nullable = false)
    private boolean marketingYn = true;
}
