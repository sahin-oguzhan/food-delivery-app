package com.oguzhan.food_delivery.dto;

public record PaymentRequestDto(String cardNumber,
                                String cardHolderName,
                                String expDate,
                                String cvv,
                                Double amount) {
}
