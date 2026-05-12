package com.oguzhan.food_delivery.controller.category;

import com.oguzhan.food_delivery.dto.category.CategoryRequest;
import com.oguzhan.food_delivery.dto.category.CategoryResponse;
import com.oguzhan.food_delivery.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryRequest));
    }
}
