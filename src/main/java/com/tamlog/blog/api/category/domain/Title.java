package com.tamlog.blog.api.category.domain;

import com.tamlog.blog.advice.custom.InvalidTitleException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static com.tamlog.blog.advice.ExceptionField.EXCEPTION_LENGTH;

@Getter
@Embeddable
public class Title {

    public static final int MAX_LENGTH = 50;

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
            throw new InvalidTitleException(MAX_LENGTH, EXCEPTION_LENGTH, 0);
        }

        if (title.length() > MAX_LENGTH) {
            throw new InvalidTitleException(MAX_LENGTH, EXCEPTION_LENGTH, title.length());
        }
    }
}