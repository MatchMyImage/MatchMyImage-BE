package com.LetMeDoWith.LetMeDoWith.model.user;

import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.model.BaseAuditModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user_social_model")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSocialAccount extends BaseAuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_social_account_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "column", nullable = false)
    private SocialProvider type;
}
