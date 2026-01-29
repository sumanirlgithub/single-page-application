package com.multithreading.synchronization.ticketbookingsystem.goodcode;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;

/**
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
        List<Thread>  threads = new ArrayList<>();
        Thread t = new Thread(() -> ticketBookingSystem.reserveTicket("Customer 1"));
        t.start();
        threads.add(t);

        for (int i = 2; i <= 10; i++) {
            AtomicInteger count = new AtomicInteger(i);
            t = new Thread(() -> ticketBookingSystem.reserveTicket("Customer " + count));
            t.start();
            threads.add(t);
        }

        for(Thread thread : threads) {
            thread.join();
        }

        System.out.println("number of remaining tickets: " + ticketBookingSystem.getAvailableTickets());


    }
}