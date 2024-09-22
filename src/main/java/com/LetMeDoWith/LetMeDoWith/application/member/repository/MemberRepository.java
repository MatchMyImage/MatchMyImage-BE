package com.LetMeDoWith.LetMeDoWith.application.member.repository;

import com.LetMeDoWith.LetMeDoWith.application.member.dto.MemberAgreementCommand;
import com.LetMeDoWith.LetMeDoWith.common.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.domain.member.Member;
import com.LetMeDoWith.LetMeDoWith.domain.member.MemberSocialAccount;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    
    Member save(Member member);
    
    void saveAgreement(Member member, MemberAgreementCommand command);
    
    void saveSocialAccount(MemberSocialAccount memberSocialAccount);
    
    Optional<Member> getMember(Long id, MemberStatus memberStatus);
    
    List<Member> getMembers(String nickname, List<MemberStatus> memberStatuses);
    
    Optional<Member> getMember(SocialProvider provider, String subject);
    
}