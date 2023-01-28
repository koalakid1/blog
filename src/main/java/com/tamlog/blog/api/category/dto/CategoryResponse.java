package com.tamlog.blog.api.category.dto;

import com.tamlog.blog.api.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class CategoryResponse implements Serializable {
    Long id;
    String title;
    Integer priority;

    @Builder
    public CategoryResponse(Long id, String title, Integer priority) {
        this.id = id;
        this.title = title;
        this.priority = priority;
    }

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle().getValue())
                .priority(category.getPriority())
                .build();
    }
}