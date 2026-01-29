package com.core.dependencyinversionprincipal.badcode.example2;

/**
 * This is a tight-coupling code.
 */
class Solution {
    public static void main(String[] args) {
        BookingService bookingService = new BookingService();
        bookingService.processBooking("Your booking is confirmed!");
    }
}

class EmailNotification {
    void notify(String message) {
        System.out.println("Email sent: " + message);
    }
}

class BookingService {
    private EmailNotification emailNotification;

    BookingService() {
        emailNotification = new EmailNotification(); // Direct dependency
    }

    void processBooking(String message) {
        emailNotification.notify(message);
    }
}