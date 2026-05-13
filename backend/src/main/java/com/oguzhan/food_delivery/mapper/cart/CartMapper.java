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

        return new CartResponse(
                cart.getId(),
                cart.getCartItems().stream().map(cartItemMapper::toCartItemResponse).toList(),
                cart.getTotalPrice()
        );
    }
}
