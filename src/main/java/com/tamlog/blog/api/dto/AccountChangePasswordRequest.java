package com.tamlog.blog.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountChangePasswordRequest {
    private String email;
    private String exPassword;
    private String newPassword;
}