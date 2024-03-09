package com.LetMeDoWith.LetMeDoWith.model.member;

import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.converter.member.SocialProviderConverter;
import com.LetMeDoWith.LetMeDoWith.model.BaseAuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member_social_account")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberSocialAccount extends BaseAuditModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_social_account_id", nullable = false)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
    @Convert(converter = SocialProviderConverter.class)
    @Column(name = "column", nullable = false)
    private SocialProvider type;
}