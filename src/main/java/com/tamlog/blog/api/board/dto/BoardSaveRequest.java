package com.tamlog.blog.api.board.dto;

import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.board.domain.Board;
import com.tamlog.blog.api.category.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardSaveRequest {
    private String title;

    private String content;

    private String categoryName;

    @Builder
    public BoardSaveRequest(String title, String content, String categoryName) {
        this.title = title;
        this.content = content;
        this.categoryName = categoryName;
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
