package com.tamlog.blog.api.board.domain;

import com.tamlog.blog.api.board.exception.InvalidTitleException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static com.tamlog.blog.support.ExceptionUtil.EXCEPTION_LENGTH;

@Getter
@Embeddable
public class Title {

    public static final int MAX_LENGTH = 200;

    @Column(name = "title", nullable = false)
    private String value;

    public Title() {
    }

    public Title(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String title) {
        if (title == null || title.isBlank()) {
            throw new InvalidTitleException(EXCEPTION_LENGTH, 0);
        }

        if (title.length() > MAX_LENGTH) {
            throw new InvalidTitleException(EXCEPTION_LENGTH, title.length());
        }
    }
}
