package com.multithreading.scheduledexecutorservice;

/**
 * Now, let's take things further by introducing the ScheduledExecutorService, which allows you to schedule tasks to run after a delay or periodically.
 * This is useful for tasks that need to be delayed or run at regular intervals, such as system health checks or timed actions.
 * Your task is to:
 *
 * Replace the current ExecutorService with a ScheduledExecutorService. (Don't forget the necessary import!)
 * Schedule two Runnable tasks:
 * The first task should run after a 2-second delay.
 * The second task should run after a 4-second delay.
 * Both tasks should print the name of the thread executing them. By switching to a scheduled executor, you will learn how to manage delayed task
 * execution in concurrent programming.
 *
 * This will help you understand how delayed execution can improve task management in real-world applications.
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// TODO: Don't forget to import ScheduledExecutorService

public class Main {
    public static void main(String[] args) {
        // TODO: Replace ExecutorService with ScheduledExecutorService
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // TODO: Schedule Runnable tasks with delays instead of immediate execution
        scheduler.schedule(() -> {
            System.out.println("Task 1 executed by " + Thread.currentThread().getName());
            System.out.println("Task executed after delay");
        }, 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            System.out.println("Task 2 executed by " + Thread.currentThread().getName());
            System.out.println("Task executed after delay");
        }, 4, TimeUnit.SECONDS);

        // Shut down the executor gracefully
        scheduler.shutdown();

        try {
            // Await termination for up to 5 seconds
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();  // Force shutdown if tasks didn't finish
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}