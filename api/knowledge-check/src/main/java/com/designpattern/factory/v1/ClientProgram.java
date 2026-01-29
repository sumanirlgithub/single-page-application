package com.designpattern.factory.v1;

public class ClientProgram {

    public static void main(String[] args) {
        Notification emailNotification = NotificationFactory.createNotification("EMAIL");

        Notification smsNotification = NotificationFactory.createNotification("SMS");
    }
}
