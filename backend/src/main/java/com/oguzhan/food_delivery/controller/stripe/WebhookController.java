package com.oguzhan.food_delivery.controller.stripe;

import com.oguzhan.food_delivery.service.order.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webhooks")
public class WebhookController {

    private final OrderService orderService;

    @Value("${STRIPE_WEBHOOK_SECRET}")
    private String endpointSecret;

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String signature) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, signature, endpointSecret);
        } catch (SignatureVerificationException e) {
            System.out.println("🚨 DİKKAT: Geçersiz Webhook İmzası!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Geçersiz imza");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bilinmeyen Hata");
        }

        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
                if (paymentIntent != null) {
                    System.out.println("✅ ÖDEME ONAYLANDI!");

                    String cartIdStr = paymentIntent.getMetadata().get("cartId");
                    Long cartId = Long.parseLong(cartIdStr);

                    orderService.createNewOrder(cartId, paymentIntent.getId());
                }
                break;
            case "payment_intent.payment_failed":
                System.out.println("❌ Ödeme başarısız oldu (Yetersiz bakiye vs.)");
                break;

            default:
                System.out.println("ℹ️ İşlenmeyen olay tipi: " + event.getType());
        }
        return ResponseEntity.ok("Başarılı");

    }
}
