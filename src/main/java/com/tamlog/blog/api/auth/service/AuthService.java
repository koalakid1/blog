package com.tamlog.blog.api.auth.service;

import com.tamlog.blog.api.account.domain.Email;
import com.tamlog.blog.api.account.dto.AccountRequest;
import com.tamlog.blog.api.account.dto.AccountResponse;
import com.tamlog.blog.api.account.exception.AccountNotFoundException;
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
        System.out.println("accountRequest.getEmail() = " + accountRequest.getEmail());
        if (accountRepository.existsByEmail(new Email(accountRequest.getEmail()))) {
            //TODO : 잘못된 EXCEPTION 코드 ... 중복에 관련된 EXCEPTION 처리 해야됨
            throw new AccountNotFoundException();
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
