package com.LetMeDoWith.LetMeDoWith.repository.member;

import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Optional<Member> findByNickname(String nickname);
    
    Optional<Member> findByEmail(String email);
    
    
    /**
     * Provider, Email의 조합으로 기 가입된 유저 여부를 조회한다.
     *
     * @param provider
     * @param email
     * @return
     */
    @Query("SELECT m from Member m left join member_social_account s on s.member = m "
        + "where s.provider = :provider and m.email = :email")
    Optional<Member> findByProviderAndEmail(SocialProvider provider, String email);
}