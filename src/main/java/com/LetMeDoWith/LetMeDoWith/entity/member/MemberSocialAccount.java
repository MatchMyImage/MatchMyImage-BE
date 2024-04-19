package com.LetMeDoWith.LetMeDoWith.entity.member;

import com.LetMeDoWith.LetMeDoWith.entity.BaseAuditEntity;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.converter.member.SocialProviderConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

@Entity(name = "member_social_account")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberSocialAccount extends BaseAuditEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_social_account_id", nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
    @Convert(converter = SocialProviderConverter.class)
    @Column(name = "provider", nullable = false)
    private SocialProvider type;
    
    public void addMember(Member member) {
        this.member = member;
        member.getSocialAccountList().add(this);
    }
}