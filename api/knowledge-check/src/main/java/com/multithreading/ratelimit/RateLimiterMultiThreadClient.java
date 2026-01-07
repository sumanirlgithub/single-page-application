package com.multithreading.ratelimit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RateLimiterMultiThreadClient {

    public static void main(String[] args) {
        SlidingWindowRateLimiter limiter =
                new SlidingWindowRateLimiter(5, 10_000); // 5 requests per 10 sec

        ExecutorService executor = Executors.newFixedThreadPool(10);

        String user = "user-999";

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                boolean allowed = limiter.allowRequest(user);
                System.out.println(Thread.currentThread().getName()
                        + " allowed? " + allowed);
            });
        }

        executor.shutdown();
    }
}
