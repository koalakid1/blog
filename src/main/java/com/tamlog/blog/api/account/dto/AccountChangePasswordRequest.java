package com.tamlog.blog.api.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

import static com.tamlog.blog.support.ValidationUtil.NOT_PATTERN_PASSWORD;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountChangePasswordRequest {
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = NOT_PATTERN_PASSWORD)
    private String currentPassword;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = NOT_PATTERN_PASSWORD)
    private String newPassword;
}