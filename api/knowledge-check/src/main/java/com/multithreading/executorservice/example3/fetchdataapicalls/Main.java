package com.multithreading.executorservice.example3.fetchdataapicalls;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Now, it's time to apply your skills by creating a Java program that simulates fetching data from four different APIs using concurrent tasks.
 *
 * Your task is to write a class named ApiFetcher to manage the API fetching logic and submit the tasks to a thread pool.
 * Ensure the ExecutorService is properly shut down after all tasks are completed. Good luck!
 */
public class Main {
    public static void main(String[] args) {
        // TODO: Create a fixed thread pool with 4 threads

        // TODO: Submit 4 API fetching tasks to the thread pool using the ApiFetcher class

        // TODO: Shut down the executor gracefully

        // TODO: Await termination for up to 5 seconds, then force shutdown if necessary

        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.execute(new ApiFetcher("API-1"));

        executor.execute(new ApiFetcher("API-2"));

        executor.execute(new ApiFetcher("API-3"));

        executor.execute(new ApiFetcher("API-4"));

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