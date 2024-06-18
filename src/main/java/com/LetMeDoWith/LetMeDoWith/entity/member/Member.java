package com.LetMeDoWith.LetMeDoWith.entity.member;

import com.LetMeDoWith.LetMeDoWith.entity.BaseAuditEntity;
import com.LetMeDoWith.LetMeDoWith.enums.member.Gender;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseAuditEntity {
    
    @OneToMany(mappedBy = "member")
    @Builder.Default
    List<MemberSocialAccount> socialAccountList = new ArrayList<>();
    
    @OneToMany(mappedBy = "member")
    @Builder.Default
    List<MemberStatusHistory> statusHistoryList = new ArrayList<>();
    
    @OneToMany(mappedBy = "followerMember")
    List<MemberFollow> followingMembers = new ArrayList<>();
    
    @OneToMany(mappedBy = "followingMember")
    List<MemberFollow> followerMembers = new ArrayList<>();
    
    @OneToOne(mappedBy = "member")
    MemberTermAgree termAgree;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;
    
    
    // OpenID Connect id token의 sub 필드와 동일
    // (sub, provider) 조합으로 uniqueness 판단함.
    @Column
    private String subject;
    
    @Column(nullable = false)
    private MemberStatus status;
    
    @Column
    private String nickname;
    
    @Column(name = "self_description")
    private String selfDescription;
    
    @Column(columnDefinition = "VARCHAR(2)")
    private Gender gender;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(nullable = false)
    private MemberType type;
    
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    
    
}