package com.oguzhan.food_delivery.service.product;

import com.oguzhan.food_delivery.dto.category.CategoryResponse;
import com.oguzhan.food_delivery.dto.product.ProductRequest;
import com.oguzhan.food_delivery.dto.product.ProductResponse;
import com.oguzhan.food_delivery.entity.Category;
import com.oguzhan.food_delivery.entity.Product;
import com.oguzhan.food_delivery.entity.Restaurant;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.mapper.product.ProductMapper;
import com.oguzhan.food_delivery.repository.CategoryRepository;
import com.oguzhan.food_delivery.repository.ProductRepository;
import com.oguzhan.food_delivery.repository.RestaurantRepository;
import com.oguzhan.food_delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    private Restaurant getAuthenticatedUserRestaurant() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        return restaurantRepository.findByOwnerId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Restoran bulunamadı!"));
    }

    @Override
    public List<ProductResponse> findAllProducts() {
        Restaurant restaurant = getAuthenticatedUserRestaurant();
        return productRepository.findByRestaurantId(restaurant.getId());

    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Restaurant restaurant = getAuthenticatedUserRestaurant();
        Category category = categoryRepository.findById(productRequest.categoryId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı."));

        if (!category.getRestaurant().getId().equals(restaurant.getId())) {
            throw new RuntimeException("Bu kategoriye ürün ekleme yetkiniz yok!");
        }

        Product product = new Product();
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setImageUrl(productRequest.imageUrl());
        product.setCategory(category);
        product.setRestaurant(restaurant);
        product.setAvailable(true);

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }
}
