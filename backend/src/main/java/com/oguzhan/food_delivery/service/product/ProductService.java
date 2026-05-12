package com.oguzhan.food_delivery.service.product;

import com.oguzhan.food_delivery.dto.product.ProductRequest;
import com.oguzhan.food_delivery.dto.product.ProductResponse;

import java.util.List;

public interface ProductService{
    List<ProductResponse> findAllProducts();
    ProductResponse createProduct(ProductRequest productRequest);
}
