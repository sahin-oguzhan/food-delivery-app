package com.oguzhan.food_delivery.mapper.cart;

import com.oguzhan.food_delivery.dto.cart.CartResponse;
import com.oguzhan.food_delivery.entity.cart.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final CartItemMapper cartItemMapper;

    public CartResponse toCartResponse(Cart cart) {
        if  (cart == null) {
            return null;
        }

        Long currentRestaurantId = null;
        if (cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
            currentRestaurantId = cart.getCartItems().getFirst().getProduct().getRestaurant().getId();
        }

        return new CartResponse(
                cart.getId(),
                currentRestaurantId,
                cart.getCartItems().stream().map(cartItemMapper::toCartItemResponse).toList(),
                cart.getTotalPrice()
        );
    }
}
