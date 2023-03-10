package com.tamlog.blog.annotations;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String email() default "test@test.com";
    String password() default "1234ascD!";
    String nickname() default "test";
    String role() default "ROLE_USER";
}
