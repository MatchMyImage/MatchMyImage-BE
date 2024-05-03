package com.LetMeDoWith.LetMeDoWith.repository.member;

import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import java.util.Optional;

public interface QMemberRepository {
    
    
    Optional<Member> findByProviderAndEmail(SocialProvider provider, String email);
    
    Optional<Member> findByProviderAndEmailAndStatus(SocialProvider provider,
        String email,
        MemberStatus status);
    
    
}