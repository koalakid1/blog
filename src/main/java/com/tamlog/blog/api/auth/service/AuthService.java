package com.tamlog.blog.api.auth.service;

import com.tamlog.blog.advice.ExceptionField;
import com.tamlog.blog.advice.custom.AlreadyExistNicknameException;
import com.tamlog.blog.api.account.domain.Email;
import com.tamlog.blog.api.account.domain.Nickname;
import com.tamlog.blog.api.account.dto.AccountRequest;
import com.tamlog.blog.api.account.dto.AccountResponse;
import com.tamlog.blog.api.account.repository.AccountRepository;
import com.tamlog.blog.api.auth.dto.TokenResponse;
import com.tamlog.blog.api.auth.exception.AlreadyExistAccountException;
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
        Email email = new Email(accountRequest.getEmail());
        validateAccount(email);

        Nickname nickname = new Nickname(accountRequest.getNickname());
        validateUniqueNickname(nickname);

        return AccountResponse.of(accountRepository.save(accountRequest.toEntity(passwordEncoder)));
    }

    public TokenResponse login(AccountRequest accountRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = accountRequest.toAuthentication();

        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authenticate = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateAccessToken(authenticate);
    }

    private void validateAccount(Email email) {
        if (accountRepository.existsByEmail(email)) {
            throw new AlreadyExistAccountException();
        }
    }

    private void validateUniqueNickname(Nickname nickname) {
        if (accountRepository.existsByNickname(nickname)) {
            throw new AlreadyExistNicknameException(ExceptionField.EXCEPTION_NICKNAME, nickname.getValue());
        };
    }
}
