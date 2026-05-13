package com.oguzhan.food_delivery.mapper.cart;

import com.oguzhan.food_delivery.dto.cart.CartItemResponse;
import com.oguzhan.food_delivery.entity.cart.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {
    public CartItemResponse toCartItemResponse(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }

        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice(),
                cartItem.getSubTotal()
        );
    }
}
