package com.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * The Subject class
 */
public class Stock {

    private String symbol;
    private double price;
    private List<StockObserver> observers = new ArrayList<>();

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public void addObserver(StockObserver observer) {
        observers.add(observer); // Add a new watcher
    }

    // Notify all observers when price changes
    public void setPrice(double newPrice) {
        this.price = newPrice;
        // Automatically notify everyone watching this stock
        for (StockObserver observer : observers) {
            observer.update(symbol, price);
        }
    }
}
