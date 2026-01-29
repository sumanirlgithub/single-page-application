package com.multithreading.synchronization.ticketbookingsystem.badcode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * let's enhance a ticket booking system to ensure it is thread-safe.
 *
 * Currently, multiple threads can reserve tickets simultaneously, which leads to double-booking issues. Your task is to:
 *
 * Add synchronization to the ticket reservation method to prevent multiple threads from reserving tickets at the same time.
 * Ensure that ticket reservations are handled accurately without double-booking.
 * In the Main class, create multiple threads to simulate customers reserving tickets concurrently. Use start() to run the threads and join()
 * to ensure the main thread waits for all reservation attempts to finish.
 *
 * By adding the synchronized keyword and correctly managing the threads, you will make the booking system reliable and prevent any double bookings.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // TODO: Create a TicketBookingSystem instance

        // TODO: Create multiple threads to simulate customers reserving tickets
        // Example: Thread t1 = new Thread(() -> bookingSystem.reserveTicket("Customer 1"));

        // TODO: Start the threads to simulate the reservations

        // TODO: Join the threads to wait for all reservation attempts to complete

        // TODO: Print the final number of remaining tickets

        TicketBookingSystem ticketBookingSystem = new TicketBookingSystem();
        List<Thread> threads = new ArrayList<>();

        Thread t = new Thread(() -> ticketBookingSystem.reserveTicket("Customer 1"));
        t.start();
        threads.add(t);
        for (int i = 2; i <= 10; i++) {
            AtomicInteger count = new AtomicInteger(i);
            t = new Thread(() -> ticketBookingSystem.reserveTicket("Customer " + count));
            t.start();
        }

        for(Thread thread : threads) {
            thread.join();
        }

        System.out.println("number of remaining tickets: " + ticketBookingSystem.getAvailableTickets());


    }
}