package com.icet.onlinefoodordering.online_food_ordering.service.impl;

import com.icet.onlinefoodordering.online_food_ordering.model.Order;
import com.icet.onlinefoodordering.online_food_ordering.response.PaymentResponse;
import com.icet.onlinefoodordering.online_food_ordering.service.OrderService;
import com.icet.onlinefoodordering.online_food_ordering.service.PaymentService;
import com.stripe.Stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderService orderService;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Override
    public PaymentResponse createPaymentLink(Order order) throws Exception {

        PaymentResponse res = new PaymentResponse();

        try {
            Stripe.apiKey = stripeSecretKey;

            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:3000/payment/success/" + order.getId())
                    .setCancelUrl("http://localhost:3000/payment/fail")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount((long) (order.getTotalPrice() * 100)) // in cents
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("minidu alwis")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);

            res.setPayment_url(session.getUrl());
            res.setStatus("success");

        } catch (Exception e) {
            // Cancel the order if payment link fails
            orderService.cancelOrder(order.getId());

            res.setPayment_url(null);
            res.setStatus("fail");
            System.err.println("Payment link creation failed, order cancelled: " + e.getMessage());
        }

        return res;
    }
}
