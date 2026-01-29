package com.multithreading.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Hello main thread");
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync(result -> result * 2)
                .thenApplyAsync(result -> result + 5);
        //completableFuture.get(); // This call blocks until the future completes
        completableFuture.thenAccept(result -> System.out.println(result)); // Non-blocking call
        System.out.println("Hello main thread, its me Suman");
    }
}
