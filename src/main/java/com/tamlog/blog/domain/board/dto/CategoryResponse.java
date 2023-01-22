package com.tamlog.blog.domain.board.dto;

import com.tamlog.blog.domain.board.Category;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CategoryResponse implements Serializable {
    Long id;
    String name;
    Integer priority;

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .priority(category.getPriority())
                .build();
    }
}