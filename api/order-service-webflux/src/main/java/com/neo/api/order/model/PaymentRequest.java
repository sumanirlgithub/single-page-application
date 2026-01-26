package com.neo.api.order.model;

import com.neo.api.order.enums.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        String orderId,             // Reference to your order
        BigDecimal amount,          // Total amount to charge
        String currency,            // e.g., USD, EUR
        PaymentMethod paymentMethod,// Enum: CREDIT_CARD, PAYPAL, etc.

        // Card data (number, CVV): should be PCI-compliant and ideally tokenized
        // Many systems send token or payment method ID instead of raw card data
        String cardNumber,          // Optional, if credit card
        String cardCvv,             // CVV
        String paymentToken,    // tokenized card or wallet -  received from frontend / payment gateway

        String cardExpiry,          // MM/YY
        String cardHolderName,      // Optional
        String customerId,          // Optional, for recurring or fraud checks
        String description          // Optional description of the payment
) {}
