package com.tamlog.blog.api.category.dto;

import com.tamlog.blog.api.category.domain.Category;
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