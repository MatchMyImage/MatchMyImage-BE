package com.LetMeDoWith.LetMeDoWith.entity.member;

import com.LetMeDoWith.LetMeDoWith.entity.BaseAuditEntity;
import com.LetMeDoWith.LetMeDoWith.enums.BaseEnum;
import com.LetMeDoWith.LetMeDoWith.enums.converter.AbstractConverter;
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
    
    
    @Column
    private String email;
    
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
    
    
    // Member에서만 사용되기 때문에 내부에 열거형 및 converter 정의함.
    @AllArgsConstructor
    @Getter
    public enum Gender implements BaseEnum {
        MALE("M", "남성"),
        FEMAIL("F", "여성");
        
        public final String code;
        public final String description;
    }
    
    public class GenderConverter extends AbstractConverter<Gender> {
        
        public GenderConverter() {super(Gender.class);}
    }
    
}