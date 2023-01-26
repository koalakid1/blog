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
public class BoardSaveRequest {
    @NotBlank(message = BLANK_TITLE)
    private String title;

    @NotBlank(message = BLANK_CONTENT)
    private String content;

    private String categoryTitle;

    @Builder
    public BoardSaveRequest(String title, String content, String categoryTitle) {
        this.title = title;
        this.content = content;
        this.categoryTitle = categoryTitle;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .build();
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
