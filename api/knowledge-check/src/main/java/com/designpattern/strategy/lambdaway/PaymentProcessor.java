package com.designpattern.strategy.lambdaway;

public class PaymentProcessor<T> {

    private PaymentStrategy<T> strategy;

    public PaymentProcessor(PaymentStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void process(double amount, T details) {
        strategy.pay(amount, details);
    }
}
