package com.tamlog.blog.api;

import com.tamlog.blog.api.dto.CategoryRequest;
import com.tamlog.blog.domain.board.CategoryService;
import com.tamlog.blog.domain.board.dto.CategoryResponse;
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
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        var categories = categoryService.getCategories();

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> postCategory(@RequestBody CategoryRequest request) {
        var category = categoryService.postCategory(request);

        return ResponseEntity.ok(category);
    }

    @PatchMapping("/{categoryId}/name")
    public ResponseEntity<CategoryResponse> updateCategoryName(@PathVariable Long categoryId, @RequestBody Map<String, String> name) {
        var response = categoryService.updateCategoryName(categoryId, name.get("name"));

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{categoryId}/priority")
    public ResponseEntity<CategoryResponse> updateCategoryPriority(@PathVariable Long categoryId, @RequestBody Map<String, Integer> priority) {
        var response = categoryService.updateCategoryPriority(categoryId, priority.get("priority"));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok().build();
    }
}
