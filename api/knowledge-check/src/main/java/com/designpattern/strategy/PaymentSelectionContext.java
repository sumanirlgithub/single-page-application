package com.designpattern.strategy;

public class PaymentSelectionContext {
    private PaymentStrategy paymentStrategy;
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    public void checkout(double amount) {
        paymentStrategy.pay(amount);
    }
}

