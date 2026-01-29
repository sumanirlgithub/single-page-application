package com.multithreading.executorservice.example1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * his program first creates a fixed thread pool with two threads using Executors.newFixedThreadPool(2). Then, it submits two Runnable tasks to the pool,
 * each printing a message to identify the executing thread.
 *
 * Once the tasks are submitted, the program shuts down the executor gracefully with shutdown(). It waits for up to five seconds using a
 * waitTermination(5, TimeUnit.SECONDS) to allow tasks to finish. If the tasks don’t complete in time, the program forces a shutdown using shutdownNow().
 */
public class Main {
    public static void main(String[] args) {
        // Create a fixed thread pool with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit Runnable tasks using execute()
        executor.execute(() -> {
            System.out.println("Task 1 executed by " + Thread.currentThread().getName());
        });

        executor.execute(() -> {
            System.out.println("Task 2 executed by " + Thread.currentThread().getName());
        });

        // Shut down the executor gracefully - tells the executor to stop accepting new tasks, but it will finish any tasks that were already submitted.
        executor.shutdown();

        try {
            // Await termination for up to 5 seconds
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();  // Force shutdown if tasks didn't finish
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}