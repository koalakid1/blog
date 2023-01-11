package com.tamlog.blog.dto;

import com.tamlog.blog.entity.Category;
import lombok.Builder;
import lombok.Data;

public class CategoryDto {
    @Data
    public static class Request {
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

    @Data
    @Builder
    public static class Response {
        Long id;
        String name;
        Integer priority;

        public static CategoryDto.Response of(Category category) {
            return CategoryDto.Response.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .priority(category.getPriority())
                    .build();
        }
    }
}
