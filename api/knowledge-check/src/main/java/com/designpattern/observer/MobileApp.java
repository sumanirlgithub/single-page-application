package com.designpattern.observer;

public class MobileApp implements StockObserver {
    @Override
    public void update(String stock, double price) {
        // React to the change
        System.out.println("Mobile: " + stock + " is now $" + price );
    }
}
