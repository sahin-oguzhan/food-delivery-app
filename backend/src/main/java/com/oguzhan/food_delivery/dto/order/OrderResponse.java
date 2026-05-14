package com.oguzhan.food_delivery.dto.order;

import com.oguzhan.food_delivery.entity.order.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long orderId,
                            String restaurantName,
                            BigDecimal totalAmount,
                            String status,
                            LocalDateTime orderDate,
                            List<OrderItemResponse> items) {
}
