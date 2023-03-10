package com.tamlog.blog.api.category.service;

import com.tamlog.blog.advice.ExistUsingResourceException;
import com.tamlog.blog.api.board.repository.BoardRepository;
import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.api.category.dto.CategoryRequest;
import com.tamlog.blog.api.category.dto.CategoryResponse;
import com.tamlog.blog.api.category.exception.CategoryNotFoundException;
import com.tamlog.blog.api.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.tamlog.blog.advice.ExceptionField.EXCEPTION_CATEGORY;
import static com.tamlog.blog.advice.ExceptionField.EXCEPTION_ID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CategoryResponse saveCategory(CategoryRequest request) {
        List<Category> categories = categoryRepository.findByPriorityGreaterThanEqual(request.getPriority());
        categories.forEach(c -> c.updatePriority(c.getPriority() + 1));

        var category = categoryRepository.save(request.toEntity());

        return CategoryResponse.of(category);
    }

    public List<CategoryResponse> findAll() {
        var categories = categoryRepository.findAll();

        return categories.stream()
                .map(CategoryResponse::of)
                .toList();
    }

    @Transactional
    public CategoryResponse updateCategoryTitle(Long categoryId, String updateTitle) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(EXCEPTION_ID, categoryId));
        category.updateTitle(updateTitle);

        return CategoryResponse.of(category);
    }

    @Transactional
    public CategoryResponse updateCategoryPriority(Long categoryId, Integer updatePriority) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(EXCEPTION_ID, categoryId));

        Integer priority = category.getPriority();
        assert priority != null;
        int compareTo = priority.compareTo(updatePriority);

        if (compareTo > 0) {
            categoryRepository.findByPriorityGreaterThanEqualAndPriorityLessThan(updatePriority, priority)
                    .forEach(c -> c.updatePriority(c.getPriority() + 1));
        } else {
            categoryRepository.findByPriorityGreaterThanAndPriorityLessThanEqual(priority, updatePriority)
                    .forEach(c -> c.updatePriority(c.getPriority() - 1));
        }
        category.updatePriority(updatePriority);

        return CategoryResponse.of(category);
    }

    @Transactional
    public void delete(Long categoryId) {
        if (boardRepository.existsByCategoryId(categoryId)) {
            throw new ExistUsingResourceException(EXCEPTION_CATEGORY, categoryId);
        }
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(EXCEPTION_ID, categoryId));
        categoryRepository.findByPriorityGreaterThanEqual(category.getPriority())
                .forEach(c -> c.updatePriority(c.getPriority() - 1));

        categoryRepository.deleteById(categoryId);
    }
}
