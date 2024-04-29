package com.LetMeDoWith.LetMeDoWith.repository.member;

import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long>, QMemberRepository {
    
    Optional<Member> findByNickname(String nickname);
    
    Optional<Member> findByIdAndStatus(Long id, MemberStatus status);
    
    Optional<Member> findByEmail(String email);
    
    
}