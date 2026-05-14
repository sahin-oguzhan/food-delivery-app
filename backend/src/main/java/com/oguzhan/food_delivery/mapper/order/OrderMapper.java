package com.oguzhan.food_delivery.mapper.order;

import com.oguzhan.food_delivery.dto.order.OrderItemResponse;
import com.oguzhan.food_delivery.dto.order.OrderResponse;
import com.oguzhan.food_delivery.entity.order.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtOrder()
                )).toList();

        return new OrderResponse(
                order.getId(),
                order.getRestaurant().getName(),
                order.getTotalAmount(),
                order.getOrderStatus().name(),
                order.getOrderDate(),
                itemResponses
        );
    }
}
