package com.designpattern.observer;

public class ClientProgram {

    public static void main(String[] args) {
        // Create a stock to watch
        Stock apple = new Stock("AAPL", 150.00);

        // Create observers
        MobileApp app = new MobileApp();
        EmailNotifier email = new EmailNotifier();

        // Subscribe them to stock
        apple.addObserver(app);
        apple.addObserver(email);

        // Change price - both observers get notified automatically!
        apple.setPrice(155.25);
        apple.setPrice(148.75);
    }
}
