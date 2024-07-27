package com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository;

import com.LetMeDoWith.LetMeDoWith.application.member.domain.Member;
import com.LetMeDoWith.LetMeDoWith.application.member.domain.MemberSocialAccount;
import com.LetMeDoWith.LetMeDoWith.common.enums.SocialProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSocialAccountJpaRepository extends JpaRepository<MemberSocialAccount, Long> {
    
    Optional<MemberSocialAccount> findByMemberAndProvider(Member member, SocialProvider provider);
}