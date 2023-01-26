package com.tamlog.blog.api.category.dto;

import com.tamlog.blog.api.category.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CategoryResponse implements Serializable {
    Long id;
    String title;
    Integer priority;

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle().getValue())
                .priority(category.getPriority())
                .build();
    }
}