package com.tamlog.blog.api.category.dto;

import com.tamlog.blog.api.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import static com.tamlog.blog.support.ValidationUtil.BLANK_TITLE;
import static com.tamlog.blog.support.ValidationUtil.NOT_POSITIVE;

@Data
@AllArgsConstructor
public class CategoryRequest {
    Long id;
    @NotBlank(message = BLANK_TITLE)
    String title;

    @Positive(message = NOT_POSITIVE)
    Integer priority;

    public Category toEntity() {
        return Category.builder()
                .id(id)
                .title(title)
                .priority(priority)
                .build();
    }
}