package com.multithreading.executorservice.example2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Let's take this a step further to better understand how managing thread pool size affects concurrent task execution.
 *
 * Change Thread Pool Size: Modify the current code that uses a fixed thread pool of two threads. Update it to create a fixed thread pool with four threads.
 * This modification will demonstrate how increasing the thread pool size allows more tasks to be executed simultaneously.
 *
 * Add More Tasks: After changing the thread pool size, also add two more tasks to be submitted to the pool. This will help illustrate how the larger
 * thread pool can handle multiple tasks concurrently, improving overall task execution time.
 *
 * By completing these tasks, you will deepen your understanding of how adjusting a thread pool's capacity can impact the execution and performance of
 * concurrent tasks.
 */
public class Main {
    public static void main(String[] args) {
        // TODO: Change the thread pool size from 2 to 4
        ExecutorService executor = Executors.newFixedThreadPool(4);

        long startTime = System.currentTimeMillis();

        // Submit Runnable tasks using execute()
        executor.execute(() -> {
            System.out.println("Task 1 executed by " + Thread.currentThread().getName());
        });

        executor.execute(() -> {
            System.out.println("Task 2 executed by " + Thread.currentThread().getName());
        });


        executor.execute(() -> {
            System.out.println("Task 3 executed by " + Thread.currentThread().getName());
        });

        executor.execute(() -> {
            System.out.println("Task 4 executed by " + Thread.currentThread().getName());
        });

        // TODO: Add two more tasks to the executor

        // Shut down the executor gracefully
        executor.shutdown();

        try {
            // Await termination for up to 5 seconds
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();  // Force shutdown if tasks didn't finish
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("Time taken during execution of all tasks: " + (System.currentTimeMillis() - startTime));
    }
}