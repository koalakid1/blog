package com.tamlog.blog.api.board.dto;

import com.tamlog.blog.api.account.dto.AccountResponse;
import com.tamlog.blog.api.board.domain.Board;
import com.tamlog.blog.api.category.dto.CategoryResponse;
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

    private LocalDateTime updatedAt;

    // TODO : dto로 변환
    private CategoryResponse category;

    private AccountResponse account;

    public BoardResponse(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.hit = entity.getHit();
        this.like = entity.getLike();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.category = CategoryResponse.of(entity.getCategory());
        this.account = AccountResponse.of(entity.getAccount());
    }
}
