package com.tamlog.blog.api.board.dto;

import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.board.domain.Board;
import com.tamlog.blog.api.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

import static com.tamlog.blog.support.ValidationUtil.BLANK_CONTENT;
import static com.tamlog.blog.support.ValidationUtil.BLANK_TITLE;

@Getter
public class BoardRequest {
    @NotBlank(message = BLANK_TITLE)
    private String title;

    @NotBlank(message = BLANK_CONTENT)
    private String content;

    @Builder
    public BoardRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Board toEntity(Category category, Account account) {
        return Board.builder()
                .title(title)
                .content(content)
                .category(category)
                .account(account)
                .build();
    }
}
