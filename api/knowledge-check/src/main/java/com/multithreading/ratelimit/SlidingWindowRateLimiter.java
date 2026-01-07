package com.multithreading.ratelimit;

/**
 * A rate limiter restricts how many requests a client (user / IP / API key) can make within a given time window (sliding-window).
 * Example - Allow 100 requests per minute per user
 */
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter {

    private final int limit;
    private final long windowSizeMillis;
    private final ConcurrentHashMap<String, Deque<Long>> userRequests = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(int limit, long windowSizeMillis) {
        this.limit = limit;
        this.windowSizeMillis = windowSizeMillis;
    }

    public boolean allowRequest(String key) {
        long now = System.currentTimeMillis();

        // If a deque already exists for this user, return it. If not, atomically create one, store it, and return it.
        Deque<Long> deque = userRequests.computeIfAbsent(
                key, k -> new ArrayDeque<>()
        );

        synchronized (deque) {
            // Remove expired timestamps
            while (!deque.isEmpty() && deque.peekFirst() <= now - windowSizeMillis) {
                deque.pollFirst();
            }

            if (deque.size() < limit) {
                deque.addLast(now);
                return true;
            }
            return false;
        }
    }
}
