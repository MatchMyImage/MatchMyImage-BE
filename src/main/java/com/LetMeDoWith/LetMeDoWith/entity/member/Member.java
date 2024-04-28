package com.LetMeDoWith.LetMeDoWith.entity.member;

import com.LetMeDoWith.LetMeDoWith.entity.BaseAuditEntity;
import com.LetMeDoWith.LetMeDoWith.enums.attributeConverter.member.MemberStatusConverter;
import com.LetMeDoWith.LetMeDoWith.enums.attributeConverter.member.MemberTypeConverter;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseAuditEntity {
    
    @OneToMany(mappedBy = "member")
    List<MemberSocialAccount> socialAccountList = new ArrayList<>();
    
    @OneToMany(mappedBy = "member")
    List<MemberStatusHistory> statusHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "followerMember")
    List<MemberFollow> followingMembers = new ArrayList<>();

    @OneToMany(mappedBy = "followingMember")
    List<MemberFollow> followerMembers = new ArrayList<>();

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;
    
    @Column
    private String email;

    @Column(nullable = false)
    private MemberStatus status;
    
    @Column
    private String nickname;
    
    @Column(name = "self_description")
    private String selfDescription;

    @Column(nullable = false)
    private MemberType type;
    
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    
    
}