package com.tamlog.blog.service;

import com.tamlog.blog.dto.CategoryDto;
import com.tamlog.blog.entity.Category;
import com.tamlog.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public CategoryDto.Response updateCategoryName(Long categoryId, String updateName) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        category.updateName(updateName);

        return CategoryDto.Response.of(category);
    }

    @Transactional
    public CategoryDto.Response updateCategoryPriority(Long categoryId, Integer updatePriority) {
        var category = categoryRepository.findById(categoryId).orElseThrow();

        Integer priority = category.getPriority();
        assert priority != null;
        int compareTo = priority.compareTo(updatePriority);
        if (compareTo > 0) {
            var categories = categoryRepository.findByPriorityGreaterThanEqualAndPriorityLessThan(updatePriority, priority);
            categories.forEach(c -> c.updatePriority(c.getPriority() + 1));
        } else if (compareTo < 0) {
            var categories = categoryRepository.findByPriorityGreaterThanAndPriorityLessThanEqual(priority, updatePriority);
            categories.forEach(c -> c.updatePriority(c.getPriority() - 1));
        }
        category.updatePriority(updatePriority);

        return CategoryDto.Response.of(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
