package com.tamlog.blog.api.category.dto;

import com.tamlog.blog.api.category.domain.Category;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import static com.tamlog.blog.support.ValidationUtil.BLANK_TITLE;
import static com.tamlog.blog.support.ValidationUtil.NOT_POSITIVE;

@Getter
public class CategoryRequest {
    @NotBlank(message = BLANK_TITLE)
    String title;

    @Positive(message = NOT_POSITIVE)
    Integer priority;

    public CategoryRequest() {
    }

    public CategoryRequest(String title, Integer priority) {
        this.title = title;
        this.priority = priority;
    }

    public Category toEntity() {
        return Category.builder()
                .title(title)
                .priority(priority)
                .build();
    }
}