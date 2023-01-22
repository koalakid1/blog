package com.tamlog.blog.annotations;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.getContext();
        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(customUser.email(), null,
                List.of(new SimpleGrantedAuthority(customUser.role())));
        context.setAuthentication(authenticationToken);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        DaoAuthenticationProvider manager = new DaoAuthenticationProvider();
//        manager.setPasswordEncoder(passwordEncoder);
//        manager.setUserDetailsService(new CustomUserDetailsService(new ));
//        ProviderManager manager = new ProviderManager();
//        Authentication authenticate = manager.authenticate(authenticationToken);
//        System.out.println(authenticate.getName());

        return context;
    }
}
