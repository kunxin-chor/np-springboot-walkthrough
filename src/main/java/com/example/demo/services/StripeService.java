package com.example.demo.services;

import com.example.demo.models.CartItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StripeService {

    @Value("${stripe.api.secretKey}")
    private String stripeSecretKey;

    @Value("${stripe.api.publicKey}")
    private String stripePublicKey;



    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public Session createCheckoutSession(List<CartItem> cartItems, String successUrl, String cancelUrl) throws StripeException {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            ProductData productData = ProductData
                    .builder()
                    .setName(item.getProduct().getName())
                    .build();

            PriceData priceData = PriceData
                    .builder()
                    .setCurrency("usd")
                    .setUnitAmountDecimal(item.getProduct().getPrice().multiply(new BigDecimal(100)))
                    .setProductData(productData)
                    .build();

            LineItem lineItem = LineItem
                    .builder()
                    .setPriceData(priceData)
                    .setQuantity((long) item.getQuantity())
                    .build();

            lineItems.add(lineItem);
        }

        SessionCreateParams params = SessionCreateParams
                .builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(cancelUrl)
                .setSuccessUrl(successUrl)
                .addAllLineItem(lineItems)
                .build();

        return Session.create(params);
    }

    public String getPublicKey() {
        return stripePublicKey;
    }
    
}