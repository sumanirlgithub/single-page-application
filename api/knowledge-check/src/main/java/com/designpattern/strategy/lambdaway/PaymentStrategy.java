package com.designpattern.strategy.lambdaway;

@FunctionalInterface
public interface PaymentStrategy<T> {
    void pay(double amount, T details);
}
