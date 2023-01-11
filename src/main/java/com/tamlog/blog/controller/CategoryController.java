package com.tamlog.blog.controller;

import com.tamlog.blog.dto.CategoryDto;
import com.tamlog.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PatchMapping("/{category_id}")
    public ResponseEntity<CategoryDto.Response> updateCategory(@PathVariable Long category_id, @RequestBody CategoryDto.Request request) {
        var response = categoryService.updateCategory(category_id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long category_id) {
        categoryService.deleteCategory(category_id);

        return ResponseEntity.ok().build();
    }
}
