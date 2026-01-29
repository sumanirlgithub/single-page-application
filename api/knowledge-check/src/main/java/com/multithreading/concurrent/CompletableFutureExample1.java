package com.multithreading.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Hello main thread");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello, world!";
        });
        completableFuture.thenAccept(result -> System.out.println(result)); // Non-blocking call
        System.out.println("Hello main thread, testing completable future non-blocking call");
        completableFuture.join(); // This will block until the future is complete.
        System.out.println("Hello main thread, its me Suman");
    }
}
