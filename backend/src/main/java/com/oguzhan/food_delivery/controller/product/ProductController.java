package com.oguzhan.food_delivery.controller.product;

import com.oguzhan.food_delivery.dto.product.ProductListResponse;
import com.oguzhan.food_delivery.dto.product.ProductRequest;
import com.oguzhan.food_delivery.dto.product.ProductResponse;
import com.oguzhan.food_delivery.dto.product.ProductUpdateRequest;
import com.oguzhan.food_delivery.service.product.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ProductListResponse> getProductsByRestaurantId(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(productService.getProductsByRestaurantId(restaurantId));
    }

    @PostMapping(value = "/restaurant", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ProductResponse> createProduct(@Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) @RequestPart("product") @Valid ProductRequest productRequest,
                                                         @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest, image));
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        return ResponseEntity.ok(productService.updateProduct(productId, productUpdateRequest));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
