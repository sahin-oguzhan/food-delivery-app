package com.oguzhan.food_delivery.controller.product;

import com.oguzhan.food_delivery.dto.product.ProductRequest;
import com.oguzhan.food_delivery.dto.product.ProductResponse;
import com.oguzhan.food_delivery.dto.product.ProductUpdateRequest;
import com.oguzhan.food_delivery.service.product.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/owner/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @PostMapping(value = "/restaurant/{restaurantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProduct(@PathVariable Long restaurantId,
                                                         @ModelAttribute("product") @Valid ProductRequest productRequest) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(restaurantId, productRequest, productRequest.image()));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        return ResponseEntity.ok(productService.updateProduct(productId, productUpdateRequest));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
