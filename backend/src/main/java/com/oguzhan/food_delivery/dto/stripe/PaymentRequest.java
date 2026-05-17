package com.oguzhan.food_delivery.dto.stripe;

import java.math.BigDecimal;

public record PaymentRequest(BigDecimal amount,
                             Long cartId) {
}
