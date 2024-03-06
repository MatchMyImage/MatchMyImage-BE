package com.LetMeDoWith.LetMeDoWith.model.user;

import com.LetMeDoWith.LetMeDoWith.model.BaseAuditModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user_follow")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFollow extends BaseAuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_follow_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User followingUser;

    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private User followedUser;
}
