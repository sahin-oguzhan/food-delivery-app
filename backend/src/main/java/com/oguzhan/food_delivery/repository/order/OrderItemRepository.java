package com.oguzhan.food_delivery.repository.order;

import com.oguzhan.food_delivery.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
