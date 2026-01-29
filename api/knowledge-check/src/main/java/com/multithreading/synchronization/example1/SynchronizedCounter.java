package com.multithreading.synchronization.example1;

public class SynchronizedCounter {
    private int count = 0;

    // TODO: Add 'synchronized' keyword to this method
    public synchronized void increment() {
        count++;
    }

    // TODO: Add 'synchronized' keyword to this method
    public synchronized int getCount() {
        return count;
    }
}