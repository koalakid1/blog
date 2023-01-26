package com.tamlog.blog.annotations;

import com.tamlog.blog.api.account.dto.AccountRequest;
import com.tamlog.blog.api.auth.service.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    private final AuthenticationManagerBuilder managerBuilder;
    private final AuthService authService;

    public WithMockCustomUserSecurityContextFactory(AuthenticationManagerBuilder managerBuilder, AuthService authService) {
        this.managerBuilder = managerBuilder;
        this.authService = authService;
    }

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        AccountRequest request = new AccountRequest(customUser.email(), customUser.password(), customUser.nickname(), "");
        authService.signup(request);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUser.email(),customUser.password());
        Authentication authenticate = managerBuilder.getObject().authenticate(authenticationToken);

        context.setAuthentication(authenticate);
        return context;
    }
}
