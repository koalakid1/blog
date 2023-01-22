package com.tamlog.blog.domain.user;

import com.tamlog.blog.api.dto.AccountChangePasswordRequest;
import com.tamlog.blog.common.exception.CustomException;
import com.tamlog.blog.common.exception.ErrorCode;
import com.tamlog.blog.common.util.SecurityUtil;
import com.tamlog.blog.domain.user.dto.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        return accountRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID));
    }
}
