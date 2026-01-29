package com.designpattern.strategy;

public class ClientMain {
    public static void main(String[] args) {
        PaymentSelectionContext paymentSelection = new PaymentSelectionContext();

        // Use credit card payment
        paymentSelection.setPaymentStrategy(new CreditCardPaymentStrategy("1234 5678 9012 3456", "12/24", "123"));
        paymentSelection.checkout(100.0);

        // Use PayPal payment
        paymentSelection.setPaymentStrategy(new PayPalPaymentStrategy("example@example.com", "password"));
        paymentSelection.checkout(200.0);
    }
}
