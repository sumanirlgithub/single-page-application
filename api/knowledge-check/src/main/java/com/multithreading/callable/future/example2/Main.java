package com.multithreading.callable.future.example2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Let's deepen our understanding of Callable, Future, and isDone().
 *
 * This time, modify the existing code to work with two different Callable tasks: one returning an Integer and the other returning a String.
 * Additionally, you will use the isDone() method to check if the tasks have completed before retrieving the results.
 *
 * Your task is to submit both tasks to the executor, use isDone() to verify if the tasks are finished, and then retrieve their results using Future.get().
 * This exercise will help reinforce handling multiple asynchronous tasks and monitoring their completion status.
 */
public class Main {
    public static void main(String[] args) {
        // Create a fixed thread pool with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // TODO: Define a Callable task that returns an Integer (e.g., 42)

        // TODO: Define another Callable task that returns a String (e.g., "Callable task completed")

        // TODO: Submit both Callable tasks using executor.submit()

        // TODO: Use isDone() with a while to check if the tasks are still pending before retrieving the results

        // TODO: Retrieve and print the results from the Future objects

        Callable<Integer> task1 = () -> {
            System.out.println("Task1 executed by " + Thread.currentThread().getName());
            return 42;
        };

        Callable<String> task2 = () -> {
            System.out.println("Task2 executed by " + Thread.currentThread().getName());
            return "Callable task completed";
        };

        Future<Integer> future1 = executor.submit(task1);
        Future<String> future2 = executor.submit(task2);

        while (true) {
            if (future1.isDone() && future2.isDone())  {
                try {

                    System.out.println(future1.get());
                    System.out.println(future2.get());

                } catch(ExecutionException | InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }

        // Shut down the executor
        executor.shutdown();
    }
}