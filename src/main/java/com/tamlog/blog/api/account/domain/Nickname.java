package com.tamlog.blog.api.account.domain;

import com.tamlog.blog.api.account.exception.InvalidNicknameException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

import static com.tamlog.blog.advice.ExceptionField.EXCEPTION_LENGTH;
import static com.tamlog.blog.advice.ExceptionField.EXCEPTION_NICKNAME;

@Getter
@Embeddable
public class Nickname {
    public static final int MAX_LENGTH = 15;

    private static final Pattern PATTERN = Pattern.compile("^[0-9a-zA-Z가-힣]+(?:\\s+[0-9a-zA-Z가-힣]+)*$");

    @Column(name = "nickname", nullable = false)
    private String value;

    protected Nickname() {
    }

    public Nickname(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidNicknameException(EXCEPTION_LENGTH, 0);
        }

        if (value.length() > MAX_LENGTH) {
            throw new InvalidNicknameException(EXCEPTION_LENGTH, value.length());
        }

        if (!PATTERN.matcher(value).matches()) {
            throw new InvalidNicknameException(EXCEPTION_NICKNAME, value);
        }
    }
}
