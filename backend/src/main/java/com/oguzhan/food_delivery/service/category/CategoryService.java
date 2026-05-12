package com.oguzhan.food_delivery.service.category;

import com.oguzhan.food_delivery.dto.category.CategoryRequest;
import com.oguzhan.food_delivery.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAllCategories();
    CategoryResponse createCategory(CategoryRequest categoryRequest);
}
