package com.tamlog.blog.service;

import com.tamlog.blog.dto.CategoryDto;
import com.tamlog.blog.entity.Category;
import com.tamlog.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryDto.Response postCategory(CategoryDto.Request request) {
        var category = categoryRepository.save(request.toEntity());
        return CategoryDto.Response.of(category);
    }

    public List<CategoryDto.Response> getCategories() {
        var categories = categoryRepository.findAll();

        return categories.stream()
                .map(CategoryDto.Response::of)
                .toList();
    }

    public CategoryDto.Response updateCategory(Long categoryId, CategoryDto.Request request) {
        var category = Category.builder()
                .id(categoryId)
                .name(request.getName())
                .priority(request.getPriority())
                .build();

        var updatedCategory = categoryRepository.save(category);

        return CategoryDto.Response.of(updatedCategory);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
