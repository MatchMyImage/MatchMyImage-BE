package com.LetMeDoWith.LetMeDoWith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LetMeDoWithApplication {

    public static void main(String[] args) {
        SpringApplication.run(LetMeDoWithApplication.class, args);
    }

}
