package com.core.dependencyinversionprincipal.goodcode.example2;

/**
 * This is loose coupling or decoupling code.
 */
class Solution {
    public static void main(String[] args) {
        NotificationService notificationService = new EmailNotification();
        BookingService bookingService = new BookingService(notificationService);
        bookingService.processBooking("Your booking is confirmed!");
    }
}

interface NotificationService {
    void notify(String message);
}

class EmailNotification implements NotificationService {

    @Override
    public void notify(String message) {
        System.out.println("Email sent: " + message);
    }
}

class BookingService {
    private NotificationService notificationService;

    BookingService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    void processBooking(String message) {
        notificationService.notify(message);
    }
}