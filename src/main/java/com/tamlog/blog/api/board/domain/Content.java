package com.tamlog.blog.api.board.domain;

import com.tamlog.blog.advice.InvalidContentException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

import static com.tamlog.blog.support.ExceptionUtil.EXCEPTION_LENGTH;

@Getter
@Embeddable
public class Content {

    public static final int MAX_LENGTH = 5000;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String value;

    protected Content() {
    }

    public Content(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String content) {
        if (content == null || content.isBlank()) {
            throw new InvalidContentException(EXCEPTION_LENGTH, 0);
        }

        if (content.length() > MAX_LENGTH) {
            throw new InvalidContentException(EXCEPTION_LENGTH, content.length());
        }
    }
}
