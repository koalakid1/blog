package com.tamlog.blog.api.account.dto;

import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.account.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Pattern;

import static com.tamlog.blog.support.ValidationUtil.NOT_PATTERN_PASSWORD;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {
    private String email;
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = NOT_PATTERN_PASSWORD
    )
    private String password;
    private String nickname;
    private String path;

    public Account toEntity(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(Role.USER)
                .path(path)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}