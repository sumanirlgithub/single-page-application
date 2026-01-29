package com.multithreading.synchronization.ticketbookingsystem.goodcode;

/**
 * By adding the synchronized keyword and correctly managing the threads, you will make the booking system reliable and prevent any double bookings.
 */
public class TicketBookingSystem {
    private int availableTickets = 10;

    // TODO: Add synchronized keyword to prevent double-booking issues
    public synchronized boolean reserveTicket(String customer) {
        if (availableTickets > 0) {
            System.out.println(customer + " reserved a ticket.");
            availableTickets--;
            return true;
        } else {
            System.out.println("Sorry, " + customer + ". No tickets available.");
            return false;
        }
    }

    public synchronized int getAvailableTickets() {
        return availableTickets;
    }
}