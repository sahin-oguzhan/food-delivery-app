package com.oguzhan.food_delivery.service.product;

import com.oguzhan.food_delivery.dto.product.ProductListResponse;
import com.oguzhan.food_delivery.dto.product.ProductRequest;
import com.oguzhan.food_delivery.dto.product.ProductResponse;
import com.oguzhan.food_delivery.dto.product.ProductUpdateRequest;
import com.oguzhan.food_delivery.entity.Category;
import com.oguzhan.food_delivery.entity.Product;
import com.oguzhan.food_delivery.entity.Restaurant;
import com.oguzhan.food_delivery.mapper.product.ProductMapper;
import com.oguzhan.food_delivery.repository.CategoryRepository;
import com.oguzhan.food_delivery.repository.ProductRepository;
import com.oguzhan.food_delivery.repository.RestaurantRepository;
import com.oguzhan.food_delivery.security.CurrentUserService;
import com.oguzhan.food_delivery.service.imageUpload.ImageUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CurrentUserService currentUserService;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final ImageUploadService imageUploadService;


    @Override
    @Cacheable(value = "restaurant_products", key = "#restaurantId")
    public ProductListResponse getProductsByRestaurantId(Long restaurantId) {
        log.info("getProductsByRestaurantId");

        List<Product> products = productRepository.findByRestaurantId(restaurantId);
        List<ProductResponse> responseList = products.stream()
                .map(productMapper::toResponse)
                .toList();

        return new ProductListResponse(responseList);
    }

    @Override
    @CacheEvict(value = "restaurant_products", key = "#result.restaurantId")
    public ProductResponse createProduct(ProductRequest productRequest, MultipartFile image) {
        Restaurant restaurant = currentUserService.getCurrentUserRestaurant();
        Category category = categoryRepository.findById(productRequest.categoryId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı."));

        if (!category.getRestaurant().getId().equals(restaurant.getId())) {
            throw new RuntimeException("Bu kategoriye ürün ekleme yetkiniz yok!");
        }

        String imageUrl = null;
        try {
            if (image != null && !image.isEmpty()) {
                imageUrl = imageUploadService.uploadImage(image);
            }
        } catch (IOException e) {
            throw new RuntimeException("Resim yüklenirken bir hata oluştu: " + e.getMessage());
        }

        Product product = new Product();
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        product.setRestaurant(restaurant);
        product.setAvailable(true);

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest productUpdateRequest) {
        Restaurant restaurant = currentUserService.getCurrentUserRestaurant();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı!"));

        if (!product.getRestaurant().getId().equals(restaurant.getId())) {
            throw new RuntimeException("Bu ürünü güncelleme yetkiniz yok!");
        }

        Category category = categoryRepository.findById(productUpdateRequest.categoryId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı!"));
        if (!category.getRestaurant().getId().equals(restaurant.getId())) {
            throw new RuntimeException("Geçersiz kategori seçimi!");
        }

        product.setName(productUpdateRequest.name());
        product.setDescription(productUpdateRequest.description());
        product.setPrice(productUpdateRequest.price());
        product.setImageUrl(productUpdateRequest.imageUrl());
        product.setAvailable(productUpdateRequest.isAvailable());
        product.setCategory(category);

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        Restaurant restaurant = currentUserService.getCurrentUserRestaurant();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı!"));

        if (!product.getRestaurant().getId().equals(restaurant.getId())) {
            throw new RuntimeException("Bu ürünü silme yetkiniz yok!");
        }

        productRepository.delete(product);
    }
}
