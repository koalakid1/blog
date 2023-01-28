package com.tamlog.blog.api.category.service;

import com.tamlog.blog.advice.ExistUsingResourceException;
import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.api.category.dto.CategoryRequest;
import com.tamlog.blog.api.category.dto.CategoryResponse;
import com.tamlog.blog.api.category.exception.CategoryNotFoundException;
import com.tamlog.blog.utils.BaseServiceTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;


class CategoryServiceTest extends BaseServiceTest {

    @Autowired
    CategoryService categoryService;

    ArrayList<Category> categories = new ArrayList<>();

    CategoryRequest newCategory;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 5; i++) {
            Category category = new Category((long) i, "title" + i, i + 1);
            categories.add(category);
        }
    }

    @AfterEach
    void clear() {
        categories.clear();
    }

    @Test
    @DisplayName(value = "카테고리가 저장 시, 카테고리 순서가 중간에 끼는 경우 테스트")
    void saveTest() {
        //given
        newCategory = new CategoryRequest( "newTitle", categories.get(3).getPriority());
        given(categoryRepository.findByPriorityGreaterThanEqual(anyInt()))
                .will(invocation -> {
                    Integer priority = invocation.getArgument(0);

                    return categories.stream()
                            .filter(category -> category.getPriority() >= priority)
                            .toList();
                });
        given(categoryRepository.save(any())).willReturn(newCategory.toEntity());

        //when
        CategoryResponse result = categoryService.saveCategory(newCategory);

        //then
        assertEquals("newTitle", result.getTitle());
        assertEquals(categories.get(3).getPriority()-1, result.getPriority());
    }

    @Test
    @DisplayName(value = "카테고리 제목 수정시 카테고리가 존재하지 않으면 오류 테스트")
    void updateTitleFailTest() {
        //given
        Long categoryId = categories.size() + 1l;
        var updateTitle = "updateTitle";

        given(categoryRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        //when & then?
        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategoryTitle(categoryId, updateTitle));
    }

    @Test
    @DisplayName(value = "카테고리 순서를 후순위로 변경 테스트")
    void updatePriorityBackTest() {
        //given
        Category updateCategory = categories.get(0);
        var categoryId = updateCategory.getId();
        var updatePriority = categories.get(categories.size()-1).getPriority();

        given(categoryRepository.findById(any()))
                .willReturn(Optional.of(updateCategory));

        given(categoryRepository.findByPriorityGreaterThanAndPriorityLessThanEqual(any(), any()))
                .will(invocation -> {
                    Integer start = invocation.getArgument(0);
                    Integer end = invocation.getArgument(1);

                    return categories.stream()
                            .filter(category -> category.getPriority() > start && category.getPriority() <= end)
                            .toList();
                });

        //when
        CategoryResponse result = categoryService.updateCategoryPriority(categoryId, updatePriority);

        //then
        assertEquals(updatePriority, updateCategory.getPriority());
        assertEquals(updatePriority - 1, categories.get(categories.size()-1).getPriority());
    }

    @Test
    @DisplayName(value = "카테고리 순서를 앞순위로 변경 테스트")
    void updatePriorityFrontTest() {
        //given
        Category updateCategory = categories.get(categories.size()-1);
        var categoryId = updateCategory.getId();
        var updatePriority = categories.get(0).getPriority();

        given(categoryRepository.findById(any()))
                .willReturn(Optional.of(updateCategory));

        given(categoryRepository.findByPriorityGreaterThanEqualAndPriorityLessThan(any(), any()))
                .will(invocation -> {
                    Integer start = invocation.getArgument(0);
                    Integer end = invocation.getArgument(1);

                    return categories.stream()
                            .filter(category -> category.getPriority() >= start && category.getPriority() < end)
                            .toList();
                });

        //when
        CategoryResponse result = categoryService.updateCategoryPriority(categoryId, updatePriority);

        //then
        assertEquals(updatePriority, updateCategory.getPriority());
        assertEquals(updatePriority + 1, categories.get(0).getPriority());
    }

    @Test
    @DisplayName(value = "카테고리 순서 수정시 카테고리가 존재하지 않으면 오류 테스트")
    void updatePriorityFailTest() {
        //given
        Long categoryId = categories.size() + 1l;
        var updatePriority = 3;

        given(categoryRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        //when & then?
        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategoryPriority(categoryId, updatePriority));
    }

    @Test
    @DisplayName(value = "카테고리 순서 수정시 카테고리의 원래 순서가 없으면 오류 테스트")
    void updatePriorityAssertFailTest() {
        //given
        Long categoryId = categories.size() + 1l;
        var updatePriority = 3;
        Category category = categories.get(categories.size() - 1);
        category.updatePriority(null);

        given(categoryRepository.findById(anyLong()))
                .willReturn(Optional.of(category));
        //when & then?
        assertThrows(AssertionError.class, () -> categoryService.updateCategoryPriority(categoryId, updatePriority));
    }

    @Test
    @DisplayName(value = "카테고리 삭제 시 카테고리가 존재하지 않으면 오류 테스트")
    void deleteFailTest() {
        //given
        Long categoryId = categories.size() + 1l;

        given(categoryRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        given(boardRepository.existsByCategoryId(anyLong()))
                .willReturn(false);

        //when & then?
        assertThrows(CategoryNotFoundException.class, () -> categoryService.delete(categoryId));
    }

    @Test
    @DisplayName(value = "카테고리 삭제 시 게시글이 카테고리를 이미 사용중이면 오류 테스트")
    void deleteExistUsingResourceFailTest() {
        //given
        Long categoryId = categories.size() + 1l;
        given(boardRepository.existsByCategoryId(anyLong()))
                .willReturn(true);

        //when & then?
        assertThrows(ExistUsingResourceException.class, () -> categoryService.delete(categoryId));
    }
}