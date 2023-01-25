package com.tamlog.blog.api.category.service;

import com.tamlog.blog.advice.CustomException;
import com.tamlog.blog.advice.ErrorCode;
import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.api.category.dto.CategoryRequest;
import com.tamlog.blog.api.category.dto.CategoryResponse;
import com.tamlog.blog.api.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse postCategory(CategoryRequest request) {
        List<Category> categories = categoryRepository.findByPriorityGreaterThanEqual(request.getPriority());
        categories.forEach(c -> c.updatePriority(c.getPriority() + 1));

        var category = categoryRepository.save(request.toEntity());

        return CategoryResponse.of(category);
    }

    public List<CategoryResponse> getCategories() {
        var categories = categoryRepository.findAll();

        return categories.stream()
                .map(CategoryResponse::of)
                .toList();
    }

    @Transactional
    public CategoryResponse updateCategoryName(Long categoryId, String updateName) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID));
        category.updateName(updateName);

        return CategoryResponse.of(category);
    }

    @Transactional
    public CategoryResponse updateCategoryPriority(Long categoryId, Integer updatePriority) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID));

        Integer priority = category.getPriority();
        assert priority != null;
        int compareTo = priority.compareTo(updatePriority);
        if (compareTo > 0) {
            categoryRepository.findByPriorityGreaterThanEqualAndPriorityLessThan(updatePriority, priority)
                    .forEach(c -> c.updatePriority(c.getPriority() + 1));
        } else if (compareTo < 0) {
            categoryRepository.findByPriorityGreaterThanAndPriorityLessThanEqual(priority, updatePriority)
                    .forEach(c -> c.updatePriority(c.getPriority() - 1));
        }
        category.updatePriority(updatePriority);

        return CategoryResponse.of(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID));
        categoryRepository.findByPriorityGreaterThanEqual(category.getPriority())
                .forEach(c -> c.updatePriority(c.getPriority() - 1));

        categoryRepository.deleteById(categoryId);
    }
}
