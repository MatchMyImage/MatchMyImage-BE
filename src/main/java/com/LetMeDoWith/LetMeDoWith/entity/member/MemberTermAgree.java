package com.LetMeDoWith.LetMeDoWith.entity.member;

import com.LetMeDoWith.LetMeDoWith.entity.BaseAuditEntity;
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
    @Column(name = "member_term_agree_id", nullable = false)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
    @Column(name = "terms_of_agree", nullable = false)
    private boolean termsOfAgree = true;
    
    @Column(nullable = false)
    private boolean privacy = true;
    
    @Column(nullable = false)
    private boolean advertisement;
}