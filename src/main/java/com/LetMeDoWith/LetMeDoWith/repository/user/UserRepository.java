package com.LetMeDoWith.LetMeDoWith.repository.user;

import com.LetMeDoWith.LetMeDoWith.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
}
