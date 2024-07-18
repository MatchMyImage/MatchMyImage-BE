package com.LetMeDoWith.LetMeDoWith.common;

import com.LetMeDoWith.LetMeDoWith.service.AuthService;
import com.LetMeDoWith.LetMeDoWith.service.SocialProviderAuthFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class BeanTest {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private BeanFactory beanFactory;
    
    @Autowired
    private SocialProviderAuthFactory socialProviderAuthFactory;
    
    @Test
    @DisplayName("application context bean Test")
    void applicationContextBeanTest() {
        
        // when
        AuthService authService1 = applicationContext.getBean(AuthService.class);
        AuthService authService2 = (AuthService) applicationContext.getBean("authService");
        
        // then
        Assertions.assertThat(authService1).isInstanceOf(AuthService.class);
        Assertions.assertThat(authService2).isInstanceOf(AuthService.class);
    }
    
    @Test
    @DisplayName("Bean Factory Test")
    void beanFactoryTest() {
        
        // when
        AuthService authService1 = beanFactory.getBean(AuthService.class);
        AuthService authService2 = (AuthService) beanFactory.getBean("authService");
        
        // then
        Assertions.assertThat(authService1).isInstanceOf(AuthService.class);
        Assertions.assertThat(authService2).isInstanceOf(AuthService.class);
    }
    
}