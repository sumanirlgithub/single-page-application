package com.multithreading.callable.future.example1;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        // Create a fixed thread pool with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Define the Callable task inline using a lambda expression
        Callable<Integer> task = () -> {
            System.out.println("Task executed by " + Thread.currentThread().getName());
            return 42;
        };

        // Submit the Callable task using submit() and get the result with Future
        Future<Integer> future = executor.submit(task);

        try {
            // Getting the result from the Future (blocking)
            Integer result = future.get();
            System.out.println("Result from Callable Task: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Shut down the executor
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}