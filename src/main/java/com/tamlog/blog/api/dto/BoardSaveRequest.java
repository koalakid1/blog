package com.tamlog.blog.api.dto;

import com.tamlog.blog.domain.board.Board;
import com.tamlog.blog.domain.board.Category;
import com.tamlog.blog.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardSaveRequest {
    private String title;

    private String content;

    private Integer hit;

    private Integer like;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    private Category category;

    private User user;

    @Builder
    public BoardSaveRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }
}
