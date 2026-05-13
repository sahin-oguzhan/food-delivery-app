package com.oguzhan.food_delivery.dto.cart;

import java.math.BigDecimal;

public record CartItemResponse(Long id,
                               Long productId,
                               String productName,
                               Integer quantity,
                               BigDecimal price,
                               BigDecimal subTotal) {
}
