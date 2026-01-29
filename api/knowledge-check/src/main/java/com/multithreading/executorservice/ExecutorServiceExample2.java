package com.multithreading.executorservice;

import java.util.concurrent.*;

public class ExecutorServiceExample2 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // task1 is an instance of Callable interface - submit(Callable<T> task) method return a value
        Future<String> future = executorService.submit(task1);

        System.out.println(future.isDone());
        try {
            String result = future.get(); // blocking call to get the results
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(future.isDone());

        executorService.shutdown();
    }

    // Callable interface has a single abstract method call()
    static Callable<String> task1 = () -> {
        String completeMsg =
                Thread.currentThread().getName() + ": task1";
        return completeMsg;
    };

}
