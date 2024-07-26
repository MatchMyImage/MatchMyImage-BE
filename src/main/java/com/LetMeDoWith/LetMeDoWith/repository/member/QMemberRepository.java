package com.LetMeDoWith.LetMeDoWith.repository.member;

import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import java.util.Optional;

public interface QMemberRepository {
    
    
    Optional<Member> findByProviderAndSubject(SocialProvider provider, String subject);
    
    Optional<Member> findByProviderAndSubjectAndStatus(SocialProvider provider,
        String subject,
        MemberStatus status);
    
    
}