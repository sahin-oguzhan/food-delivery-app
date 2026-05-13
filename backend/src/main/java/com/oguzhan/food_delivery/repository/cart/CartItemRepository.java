package com.oguzhan.food_delivery.repository.cart;

import com.oguzhan.food_delivery.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
