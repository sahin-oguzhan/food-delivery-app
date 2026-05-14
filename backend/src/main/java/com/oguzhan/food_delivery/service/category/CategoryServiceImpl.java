package com.oguzhan.food_delivery.service.category;

import com.oguzhan.food_delivery.dto.category.CategoryRequest;
import com.oguzhan.food_delivery.dto.category.CategoryResponse;
import com.oguzhan.food_delivery.entity.Category;
import com.oguzhan.food_delivery.entity.Restaurant;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.mapper.category.CategoryMapper;
import com.oguzhan.food_delivery.repository.CategoryRepository;
import com.oguzhan.food_delivery.repository.RestaurantRepository;
import com.oguzhan.food_delivery.repository.UserRepository;
import com.oguzhan.food_delivery.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final CurrentUserService currentUserService;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> findAllCategories() {
        Restaurant restaurant = currentUserService.getCurrentUserRestaurant();

        List<Category> categories = categoryRepository.findByRestaurantId(restaurant.getId());

        return categories.stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Restaurant restaurant = currentUserService.getCurrentUserRestaurant();

        Category category = new Category();
        category.setName(categoryRequest.name());
        category.setDescription(categoryRequest.description());
        category.setRestaurant(restaurant);

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }
}
