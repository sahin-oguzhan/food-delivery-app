package com.oguzhan.food_delivery.service.restaurant;

import com.oguzhan.food_delivery.dto.customer.MenuCategoryResponse;
import com.oguzhan.food_delivery.dto.customer.MenuItemResponse;
import com.oguzhan.food_delivery.dto.customer.RestaurantMenuResponse;
import com.oguzhan.food_delivery.dto.product.ProductResponse;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantListResponse;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantRequest;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantResponse;
import com.oguzhan.food_delivery.dto.restaurant.RestaurantUpdateRequest;
import com.oguzhan.food_delivery.entity.Category;
import com.oguzhan.food_delivery.entity.Product;
import com.oguzhan.food_delivery.entity.Restaurant;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.mapper.restaurant.RestaurantMapper;
import com.oguzhan.food_delivery.repository.CategoryRepository;
import com.oguzhan.food_delivery.repository.ProductRepository;
import com.oguzhan.food_delivery.repository.RestaurantRepository;
import com.oguzhan.food_delivery.repository.UserRepository;
import com.oguzhan.food_delivery.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final RestaurantMapper restaurantMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public RestaurantResponse getRestaurant(Long restaurantId) {
        Restaurant restaurant  = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restoran bulunamadı!"));

        return restaurantMapper.toResponse(restaurant);
    }

    @Override
    @Cacheable(value = "restaurants")
    public RestaurantListResponse getAllRestaurants() {
        log.info("getAllRestaurants");
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantResponse> responseList = restaurants.stream()
                .map(restaurantMapper::toResponse)
                .toList();
        return new RestaurantListResponse(responseList);
    }

    @Override
    public RestaurantMenuResponse getRestaurantMenu(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restoran bulunamadı!"));

        List<Category> categories = categoryRepository.findByRestaurantId(restaurantId);
        List<ProductResponse> availableProducts = productRepository.findByRestaurantIdAndIsAvailableTrue(restaurantId);

        List<MenuCategoryResponse> categoryResponses = categories.stream().map(
                category -> {
                    List<MenuItemResponse> items = availableProducts.stream()
                            .filter(product -> product.categoryId().equals(category.getId()))
                            .map(product -> new MenuItemResponse(
                                    product.id(),
                                    product.name(),
                                    product.description(),
                                    product.price(),
                                    product.imageUrl()
                            )).toList();

                    return new MenuCategoryResponse(category.getId(), category.getName(), category.getDescription(), items);
                })
                .filter(menuCategoryResponse -> !menuCategoryResponse.items().isEmpty())
                .toList();

        return new RestaurantMenuResponse(restaurant.getId(), restaurant.getName(), categoryResponses);
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        if (restaurantRepository.findByOwnerId(currentUser.getId()).isPresent()) {
            throw new RuntimeException("Zaten bir restoranınız bulunuyor!");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setAddress(restaurantRequest.address());
        restaurant.setPhoneNumber(restaurantRequest.phoneNumber());
        restaurant.setDescription(restaurantRequest.description());
        restaurant.setOwner(currentUser);

        return restaurantMapper.toResponse(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public RestaurantResponse updateRestaurant(RestaurantUpdateRequest restaurantUpdateRequest) {
        Restaurant restaurant = currentUserService.getCurrentUserRestaurant();

        restaurant.setName(restaurantUpdateRequest.name());
        restaurant.setAddress(restaurantUpdateRequest.address());
        restaurant.setPhoneNumber(restaurantUpdateRequest.phoneNumber());
        restaurant.setDescription(restaurantUpdateRequest.description());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        return restaurantMapper.toResponse(updatedRestaurant);
    }
}
