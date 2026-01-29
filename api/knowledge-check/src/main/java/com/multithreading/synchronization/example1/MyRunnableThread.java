package com.multithreading.synchronization.example1;

public class MyRunnableThread implements Runnable {
    SynchronizedCounter counter = new SynchronizedCounter();

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }

}
