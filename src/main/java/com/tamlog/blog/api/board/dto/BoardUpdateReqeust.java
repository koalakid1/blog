package com.tamlog.blog.api.board.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class BoardUpdateReqeust {
    @NotBlank(message = "제목이 없습니다.")
    String title;
    @NotBlank(message = "본문이 없습니다.")
    String content;

    public BoardUpdateReqeust() {
    }

    public BoardUpdateReqeust(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
