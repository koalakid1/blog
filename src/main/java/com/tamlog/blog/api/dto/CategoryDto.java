package com.tamlog.blog.api.dto;

import com.tamlog.blog.domain.board.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

public class CategoryDto {
    @Data
    @AllArgsConstructor
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
    public static class Response implements Serializable {
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
