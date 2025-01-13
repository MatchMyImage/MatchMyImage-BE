package com.LetMeDoWith.LetMeDoWith.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Retention(RetentionPolicy.RUNTIME)
@Component
@Service
@Target(ElementType.TYPE)
public @interface DomainService {

}
