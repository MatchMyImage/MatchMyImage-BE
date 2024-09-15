package com.LetMeDoWith.LetMeDoWith.domain.member;

import com.LetMeDoWith.LetMeDoWith.common.entity.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member_term_agree")
public class MemberTermAgree extends BaseAuditEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
    @Builder.Default
    @Column(name = "terms_of_agree", nullable = false)
    private boolean termsOfAgree = true;
    
    @Builder.Default
    @Column(nullable = false)
    private boolean privacy = true;
    
    @Column(nullable = false)
    private boolean advertisement;
    
    public static MemberTermAgree initialize(Member member,
                                             boolean termsOfAgree,
                                             boolean privacy,
                                             boolean advertisement) {
        MemberTermAgree memberTermAgree = MemberTermAgree.builder()
                                                         .member(member)
                                                         .termsOfAgree(termsOfAgree)
                                                         .privacy(privacy)
                                                         .advertisement(advertisement)
                                                         .build();
        
        member.linkTermAgree(memberTermAgree);
        
        return memberTermAgree;
    }
}