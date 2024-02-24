package com.LetMeDoWith.LetMeDoWith.model.user;

import com.LetMeDoWith.LetMeDoWith.model.Audit;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column
    private String email;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "self_description")
    private String selfDescription;

    @Column(nullable = false)
    private String type;

    @Column(name = "user_profile_image_url")
    private String userProfileImageUrl;

    @Column(name = "marketing_term_agree_yn", nullable = false)
    private boolean marketingTermAgreeYn = false;

    @Builder
    public User(String email, String status, String nickname, String selfDescription, String type, String userProfileImageUrl, boolean marketingTermAgreeYn) {
        this.email = email;
        this.status = status;
        this.nickname = nickname;
        this.selfDescription = selfDescription;
        this.type = type;
        this.userProfileImageUrl = userProfileImageUrl;
        this.marketingTermAgreeYn = marketingTermAgreeYn;
    }
}
