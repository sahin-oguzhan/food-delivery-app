package com.oguzhan.food_delivery.dto.order;

import java.math.BigDecimal;

public record OrderItemResponse(Long productId,
                                String productName,
                                Integer quantity,
                                BigDecimal priceAtOrder) {
}
