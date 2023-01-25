package com.tamlog.blog.api.category.dto;

import com.tamlog.blog.api.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRequest {
    Long id;
    String name;
    Integer priority;

    public Category toEntity() {
        return Category.builder()
                .id(id)
                .name(name)
                .priority(priority)
                .build();
    }
}