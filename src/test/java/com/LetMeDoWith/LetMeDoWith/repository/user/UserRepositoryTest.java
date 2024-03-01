package com.LetMeDoWith.LetMeDoWith.repository.user;

import com.LetMeDoWith.LetMeDoWith.enums.user.UserStatus;
import com.LetMeDoWith.LetMeDoWith.enums.user.UserType;
import com.LetMeDoWith.LetMeDoWith.model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("새로운 객체 삽입 테스트")
    void test_insert_new() {
        User testUserObj = User.builder().email("test@email.com").nickname("nickname")
                .selfDescription("self desc")
                .status(UserStatus.NORMAL)
                .type(UserType.USER)
                .userProfileImageUrl("image.jpeg")
                .marketingTermAgreeYn(true)
                .build();

        userRepository.save(testUserObj);

        Optional<User> user = userRepository.findByNickname("nickname");

        assertInstanceOf(User.class, user.get());
        assertEquals(user.get().getNickname(), "nickname");
        assertEquals(user.get().getStatus(), UserStatus.NORMAL);
    }

    @Test
    @DisplayName("객체 삭제 테스트")
    void test_delete() {
        User testUserObj = User.builder().email("test@email.com").nickname("nickname")
                .selfDescription("self desc")
                .status(UserStatus.NORMAL)
                .type(UserType.USER)
                .userProfileImageUrl("image.jpeg")
                .marketingTermAgreeYn(true)
                .build();

        userRepository.save(testUserObj);

        Optional<User> user = userRepository.findByNickname("nickname");

        assertInstanceOf(User.class, user.get());
        assertEquals(user.get().getNickname(), "nickname");


        // delete
        userRepository.deleteAll();
        Optional<User> userAfterDelete = userRepository.findByNickname("nickname");

        Assertions.assertThrows(NoSuchElementException.class, userAfterDelete::get);
    }
}