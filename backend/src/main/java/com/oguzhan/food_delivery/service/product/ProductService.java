package com.oguzhan.food_delivery.service.product;

import com.oguzhan.food_delivery.dto.product.ProductRequest;
import com.oguzhan.food_delivery.dto.product.ProductResponse;
import com.oguzhan.food_delivery.dto.product.ProductUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService{
    List<ProductResponse> findAllProducts();
    ProductResponse createProduct(ProductRequest productRequest, MultipartFile image);
    ProductResponse updateProduct(Long productId, ProductUpdateRequest productUpdateRequest);
    void deleteProduct(Long productId);
}
