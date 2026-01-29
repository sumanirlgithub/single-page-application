package com.multithreading.executorservice;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample1 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // task1 is an instance of Runnable interface - execute(Runnable command) method doesn't return any value
        executorService.execute(task1);
        executorService.execute(task2);
        executorService.execute(task3);

        executorService.shutdown();
    }

    // Runnable interface has a single abstract method run()
    static Runnable task1 = () -> {
        String completeMsg =
                Thread.currentThread().getName() + ": task1";
        System.out.println(completeMsg);
    };

    static Runnable task2 = () -> {
        String completeMsg =
                Thread.currentThread().getName() + ": task2";
        System.out.println(completeMsg);
    };

    static Runnable task3 = () -> {
        String completeMsg =
                Thread.currentThread().getName() + ": task3";
        System.out.println(completeMsg);
    };

}
