package com.oguzhan.food_delivery.service.stripe;

import com.oguzhan.food_delivery.dto.stripe.PaymentRequest;
import com.oguzhan.food_delivery.dto.stripe.PaymentResponse;
import com.oguzhan.food_delivery.entity.order.Order;
import com.oguzhan.food_delivery.repository.order.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderRepository orderRepository;
    public PaymentResponse createPaymentIntent(PaymentRequest request) {
        try {
            long amount = request.amount().multiply(new BigDecimal(100)).longValue();
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency("try")
                    .setDescription("Food Delivery Sipariş Ödemesi")
                    .putMetadata("cartId", request.cartId().toString())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            return new PaymentResponse(paymentIntent.getClientSecret());
        }
        catch (StripeException e) {
            throw new RuntimeException("Ödeme işlemi başlatılamadı: " + e.getMessage());
        }
    }
}
