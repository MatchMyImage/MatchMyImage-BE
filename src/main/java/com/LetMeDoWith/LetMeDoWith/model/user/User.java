package com.LetMeDoWith.LetMeDoWith.model.user;

import com.LetMeDoWith.LetMeDoWith.enums.converter.user.UserStatusConverter;
import com.LetMeDoWith.LetMeDoWith.enums.converter.user.UserTypeConverter;
import com.LetMeDoWith.LetMeDoWith.enums.user.UserStatus;
import com.LetMeDoWith.LetMeDoWith.enums.user.UserType;
import com.LetMeDoWith.LetMeDoWith.model.BaseAuditModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseAuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column
    private String email;

    @Convert(converter = UserStatusConverter.class)
    @Column(nullable = false)
    private UserStatus status;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "self_description")
    private String selfDescription;

    @Convert(converter = UserTypeConverter.class)
    @Column(nullable = false)
    private UserType type;

    @Column(name = "user_profile_image_url")
    private String userProfileImageUrl;

    @Column(name = "marketing_term_agree_yn", nullable = false)
    private boolean marketingTermAgreeYn = false;

}
