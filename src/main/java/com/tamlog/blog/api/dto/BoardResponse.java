package com.tamlog.blog.api.dto;

import com.tamlog.blog.domain.board.Board;
import com.tamlog.blog.domain.board.Category;
import com.tamlog.blog.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponse {
    private Long id;
    private String title;

    private String content;

    private Integer hit;

    private Integer like;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    // TODO : dto로 변환
    private Category category;

    private User user;

    public BoardResponse(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.hit = entity.getHit();
        this.like = entity.getLike();
        this.createdAt = entity.getCreatedAt();
        this.updateAt = entity.getUpdateAt();
        this.category = entity.getCategory();
        this.user = entity.getUser();
    }
}
