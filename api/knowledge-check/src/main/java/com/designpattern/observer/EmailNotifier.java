package com.designpattern.observer;

public class EmailNotifier implements StockObserver {

    @Override
    public void update(String stock, double price) {
        // React to the change
        System.out.println("Email: " + stock + " alert - $" + price );
    }
}
