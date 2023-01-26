package com.tamlog.blog.api.board.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

import static com.tamlog.blog.support.ValidationUtil.BLANK_CONTENT;
import static com.tamlog.blog.support.ValidationUtil.BLANK_TITLE;


@Getter
public class BoardUpdateReqeust {
    @NotBlank(message = BLANK_TITLE)
    String title;
    @NotBlank(message = BLANK_CONTENT)
    String content;

    public BoardUpdateReqeust() {
    }

    public BoardUpdateReqeust(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
