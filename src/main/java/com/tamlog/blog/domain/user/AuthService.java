package com.tamlog.blog.domain.user;

import com.tamlog.blog.api.dto.AccountRequest;
import com.tamlog.blog.common.jwt.TokenProvider;
import com.tamlog.blog.domain.user.dto.AccountResponse;
import com.tamlog.blog.domain.user.dto.TokenResponse;
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

        Authentication authenticate = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authenticate);
    }
}
