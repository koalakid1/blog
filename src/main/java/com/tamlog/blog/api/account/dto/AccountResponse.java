package com.tamlog.blog.api.account.dto;

import com.tamlog.blog.api.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private String email;
    private String nickname;

    public static AccountResponse of(Account account) {
        return AccountResponse.builder()
                .email(account.getEmail().getValue())
                .nickname(account.getNickname().getValue())
                .build();
    }
}