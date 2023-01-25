package com.tamlog.blog.api.account.service;

import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.account.dto.AccountChangePasswordRequest;
import com.tamlog.blog.api.account.dto.AccountResponse;
import com.tamlog.blog.api.account.exception.AccountNotFoundException;
import com.tamlog.blog.api.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tamlog.blog.support.SecurityUtil.getCurrentAccountId;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountResponse findCurrentUser() {
        return AccountResponse.of(findBySecurity());
    }

    @Transactional
    public AccountResponse updateUserNickname(String nickname) {
        Account account = findBySecurity();

        account.updateNickname(nickname);

        return AccountResponse.of(account);
    }

    @Transactional
    public AccountResponse updateUserPassword(AccountChangePasswordRequest request) {
        Account account = findBySecurity();

        if (!passwordEncoder.matches(account.getPassword(), request.getExPassword())){
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        account.updatePassword(passwordEncoder.encode(request.getNewPassword()));

        return AccountResponse.of(account);
    }

    private Account findBySecurity() {
        return accountRepository.findById(getCurrentAccountId())
                .orElseThrow(AccountNotFoundException::new);
    }
}
