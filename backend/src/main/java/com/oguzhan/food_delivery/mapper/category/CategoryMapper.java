package com.oguzhan.food_delivery.mapper.category;

import com.oguzhan.food_delivery.dto.category.CategoryResponse;
import com.oguzhan.food_delivery.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getRestaurant().getId()
        );
    }
}
