package com.multithreading.synchronization.ticketbookingsystem.badcode;

public class TicketBookingSystem {
    private int availableTickets = 10;

    // TODO: Add synchronized keyword to prevent double-booking issues
    public boolean reserveTicket(String customer) {
        if (availableTickets > 0) {
            System.out.println(customer + " reserved a ticket.");
            availableTickets--;
            return true;
        } else {
            System.out.println("Sorry, " + customer + ". No tickets available.");
            return false;
        }
    }

    public int getAvailableTickets() {
        return availableTickets;
    }
}