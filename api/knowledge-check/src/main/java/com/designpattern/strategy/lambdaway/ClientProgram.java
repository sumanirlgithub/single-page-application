package com.designpattern.strategy.lambdaway;

public class ClientProgram {

    public static void main(String[] args) {

        // Strategy 1: Credit Card Payment
        PaymentStrategy<CreditCardDetails> creditCardPayment = (amount, card) -> {
            System.out.println("Processing credit card...");
            System.out.println("Card Holder: " + card.getHolderName());
            System.out.println("Card Number: " + card.getCardNumber());
            System.out.println("Charged: $" + amount);
        };

        // Strategy 2: PayPal Payment
        PaymentStrategy<PaypalAccount> payPalPayment = (amount, account) -> {
            System.out.println("Processing PayPal...");
            System.out.println("Account: " + account.getEmail());
            System.out.println("Charged: $" + amount);
        };

        // ----- USE CASES -----

        // 1. Card payment
        PaymentProcessor<CreditCardDetails> cardProcessor =
                new PaymentProcessor<>(creditCardPayment);

        cardProcessor.process(100D,
                new CreditCardDetails("4111-1111-1111-1111", "John Doe", "123"));


        // 2. PayPal payment
        PaymentProcessor<PaypalAccount> payPalProcessor =
                new PaymentProcessor<>(payPalPayment);

        payPalProcessor.process(250D,
                new PaypalAccount("john@example.com"));
    }
}
