package com.LetMeDoWith.LetMeDoWith.repository.user;

import com.LetMeDoWith.LetMeDoWith.model.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {
    
    Optional<Member> findByNickname(String nickname);
}