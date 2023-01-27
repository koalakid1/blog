package com.tamlog.blog.api.account.domain;

import com.tamlog.blog.api.account.exception.InvalidNicknameException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

import static com.tamlog.blog.advice.ExceptionField.EXCEPTION_NICKNAME;

@Getter
@Embeddable
public class Nickname {
    private static final Pattern PATTERN = Pattern.compile("^[0-9a-zA-Z가-힣]+(?:\\s+[0-9a-zA-Z가-힣]+)*${1,15}");

    // TODO : 유니크 조건 추가해야됨. 테이블도 추가해야됨 ..
    @Column(name = "nickname", nullable = false, unique = true)
    private String value;

    protected Nickname() {
    }

    public Nickname(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new InvalidNicknameException(EXCEPTION_NICKNAME, value);
        }
    }
}
