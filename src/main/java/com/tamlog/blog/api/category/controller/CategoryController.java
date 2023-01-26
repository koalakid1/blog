package com.tamlog.blog.api.category.controller;

import com.tamlog.blog.api.category.dto.CategoryRequest;
import com.tamlog.blog.api.category.dto.CategoryResponse;
import com.tamlog.blog.api.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        var categories = categoryService.findAll();

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@RequestBody CategoryRequest request) {
        var category = categoryService.saveCategory(request);

        return ResponseEntity.ok(category);
    }

    @PatchMapping("/{categoryId}/title")
    public ResponseEntity<CategoryResponse> updateCategoryTitle(@PathVariable Long categoryId, @RequestBody Map<String, String> title) {
        var response = categoryService.updateCategoryTitle(categoryId, title.get("title"));

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{categoryId}/priority")
    public ResponseEntity<CategoryResponse> updateCategoryPriority(@PathVariable Long categoryId, @RequestBody Map<String, Integer> priority) {
        var response = categoryService.updateCategoryPriority(categoryId, priority.get("priority"));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> delete(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);

        return ResponseEntity.ok().build();
    }
}
