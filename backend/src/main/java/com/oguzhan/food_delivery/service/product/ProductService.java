package com.oguzhan.food_delivery.service.product;

import com.oguzhan.food_delivery.dto.product.ProductRequest;
import com.oguzhan.food_delivery.dto.product.ProductResponse;
import com.oguzhan.food_delivery.dto.product.ProductUpdateRequest;

import java.util.List;

public interface ProductService{
    List<ProductResponse> findAllProducts();
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long productId, ProductUpdateRequest productUpdateRequest);
    void deleteProduct(Long productId);
}
