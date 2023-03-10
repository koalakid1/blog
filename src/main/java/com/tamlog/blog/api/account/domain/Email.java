package com.tamlog.blog.api.account.domain;

import com.tamlog.blog.api.account.exception.InvalidEmailException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

import static com.tamlog.blog.advice.ExceptionField.EXCEPTION_EMAIL;

@Getter
@Embeddable
public class Email {
    public static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9.%+-]+@[a-zA-Z]+.[a-zA-Z]{2,}+.[a-zA-Z]+$");

    @Column(name = "email", nullable = false, unique = true)
    private String value;

    protected Email() {
    }

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new InvalidEmailException(EXCEPTION_EMAIL, value);
        }
    }
}
