package com.tamlog.blog.api.account.repository;

import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.account.domain.Email;
import com.tamlog.blog.api.account.domain.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(Email email);
    Boolean existsByEmail(Email email);

    Boolean existsByNickname(Nickname nickname);
}