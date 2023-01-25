package com.tamlog.blog.api.auth.service;

import com.tamlog.blog.api.account.dto.AccountRequest;
import com.tamlog.blog.api.account.dto.AccountResponse;
import com.tamlog.blog.api.account.repository.AccountRepository;
import com.tamlog.blog.api.auth.dto.TokenResponse;
import com.tamlog.blog.support.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManagerBuilder managerBuilder;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AccountResponse signup(AccountRequest accountRequest) {
        if (accountRepository.existsByEmail(accountRequest.getEmail())) {
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        return AccountResponse.of(accountRepository.save(accountRequest.toEntity(passwordEncoder)));
    }

    public TokenResponse login(AccountRequest accountRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = accountRequest.toAuthentication();

        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authenticate = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateAccessToken(authenticate);
    }
}
