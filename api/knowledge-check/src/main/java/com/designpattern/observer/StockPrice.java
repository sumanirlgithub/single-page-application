package com.designpattern.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StockPrice {

    private String symbol;
    private double price;
    private List<Consumer<StockPrice>> observers = new ArrayList<>();

    public void addObserver(Consumer<StockPrice> observer) {
        observers.add(observer);
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
        System.out.println("Price updated for " + symbol + ": $" + price);
        observers.forEach(observer -> observer.accept(this));
    }
}
