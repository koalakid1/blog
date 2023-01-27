package com.tamlog.blog.api.account.service;

import com.tamlog.blog.advice.ExceptionField;
import com.tamlog.blog.advice.custom.AlreadyExistNicknameException;
import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.account.domain.Nickname;
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
        Nickname validNickname = new Nickname(nickname);
        validateUniqueNickname(validNickname);
        account.updateNickname(validNickname);

        return AccountResponse.of(account);
    }

    @Transactional
    public AccountResponse updateUserPassword(AccountChangePasswordRequest request) {
        Account account = findBySecurity();

        account.updatePassword(request.getCurrentPassword(), request.getNewPassword(), passwordEncoder);

        return AccountResponse.of(account);
    }

    private Account findBySecurity() {
        return accountRepository.findById(getCurrentAccountId())
                .orElseThrow(AccountNotFoundException::new);
    }

    private void validateUniqueNickname(Nickname nickname) {
        if (accountRepository.existsByNickname(nickname)) {
            throw new AlreadyExistNicknameException(ExceptionField.EXCEPTION_NICKNAME, nickname.getValue());
        };
    }
}
