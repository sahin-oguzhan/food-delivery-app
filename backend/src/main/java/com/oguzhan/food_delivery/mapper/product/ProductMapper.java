package com.oguzhan.food_delivery.mapper.product;

import com.oguzhan.food_delivery.dto.product.ProductResponse;
import com.oguzhan.food_delivery.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        if (product == null) return null;

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.isAvailable(),
                product.getRestaurant().getName(),
                product.getRestaurant().getId(),
                product.getCategory().getName(),
                product.getCategory().getId()
        );
    }
}
