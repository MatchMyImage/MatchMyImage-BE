package com.LetMeDoWith.LetMeDoWith.entity.member;

import com.LetMeDoWith.LetMeDoWith.entity.BaseAuditEntity;
import com.LetMeDoWith.LetMeDoWith.enums.converter.member.MemberStatusConverter;
import com.LetMeDoWith.LetMeDoWith.enums.converter.member.MemberTypeConverter;
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
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;
    
    @Column
    private String email;
    
    @Convert(converter = MemberStatusConverter.class)
    @Column(nullable = false)
    private MemberStatus status;
    
    @Column
    private String nickname;
    
    @Column(name = "self_description")
    private String selfDescription;
    
    @Convert(converter = MemberTypeConverter.class)
    @Column(nullable = false)
    private MemberType type;
    
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    
    
    /**
     * 소셜 인증되었을 때, 임시 회원 객체를 생성한다.
     * <p>
     * 이후 유저가 회원가입 프로세스를 완료할 때 임시 회원 객체의 나머지 컬럼의 데이터가 업데이트된다.
     *
     * @param email 임시 회원의 이메일
     * @return 임시 회원 객체
     */
    public static Member createTemporalMember(String email) {
        return Member.builder()
                     .email(email)
                     .type(MemberType.USER)
                     .status(MemberStatus.SOCIAL_AUTHENTICATED)
                     .build();
    }
}