package com.tamlog.blog.controller;

import com.tamlog.blog.dto.CategoryDto;
import com.tamlog.blog.service.CategoryService;
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
    public ResponseEntity<List<CategoryDto.Response>> getCategories() {
        var categories = categoryService.getCategories();

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto.Response> postCategory(@RequestBody CategoryDto.Request request) {
        var category = categoryService.postCategory(request);

        return ResponseEntity.ok(category);
    }

    @PatchMapping("/{category_id}/name")
    public ResponseEntity<CategoryDto.Response> updateCategoryName(@PathVariable Long category_id, @RequestBody Map<String, String> name) {
        var response = categoryService.updateCategoryName(category_id, name.get("name"));

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{category_id}/priority")
    public ResponseEntity<CategoryDto.Response> updateCategoryPriority(@PathVariable Long category_id, @RequestBody Map<String, Integer> priority) {
        var response = categoryService.updateCategoryPriority(category_id, priority.get("priority"));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long category_id) {
        categoryService.deleteCategory(category_id);

        return ResponseEntity.ok().build();
    }
}
