package com.tamlog.blog.api.account.domain;

import com.tamlog.blog.api.account.exception.InvalidPasswordException;
import com.tamlog.blog.api.account.exception.InvalidPasswordFormatException;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@Embeddable
public class Password {

    private static final Pattern PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$");
    @Column(name = "password", nullable = false)
    private String value;

    protected Password() {
    }

    public Password(String value) {
        this.value = value;
    }

    public void updatePassword(String nowPassword, String newPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(this.value, nowPassword)) {
            throw new InvalidPasswordException();
        }

        validate(newPassword);

        this.value = passwordEncoder.encode(newPassword);
    }

    private void validate(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new InvalidPasswordFormatException();
        }
    }
}
