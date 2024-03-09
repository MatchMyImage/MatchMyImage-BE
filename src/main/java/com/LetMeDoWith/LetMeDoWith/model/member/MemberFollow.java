package com.LetMeDoWith.LetMeDoWith.model.member;

import com.LetMeDoWith.LetMeDoWith.model.BaseAuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member_follow")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberFollow extends BaseAuditModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_follow_id", nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Member followingMember;
    
    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private Member followedMember;
}