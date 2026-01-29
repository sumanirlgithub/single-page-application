package com.multithreading.sleepjoin.example1;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // TODO: Create threads for red, green, and yellow lights
        // TODO: Start the light threads in correct order
        // TODO: Use join to make sure each thread finishes before starting the next one

        Runnable runnable = new RedLight();
        Thread redLightThread = new Thread(runnable);
        redLightThread.start();
        redLightThread.join(); // this will ensure the main thread (that calls redLightThread) will be suspended until redLightThread is finished

        runnable = new GreenLight();
        Thread greenLightThread = new Thread(runnable);
        greenLightThread.start();
        greenLightThread.join(); // this will ensure the main thread (that calls greenLightThread) will be suspended until greenLightThread is finished

        runnable = new YellowLight();
        Thread yellowLightThread = new Thread(runnable);
        yellowLightThread.start();
    }
}