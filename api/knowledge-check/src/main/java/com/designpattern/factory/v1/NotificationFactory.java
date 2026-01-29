package com.designpattern.factory.v1;

public class NotificationFactory {

    public static Notification createNotification(String type) {
        switch (type.toUpperCase()) {
            case "EMAIL":
                return new EmailNotification();
            case "SMS":
                return new SMSNotification();
            case "PUSH":
                return new PushNotification();
            default:
                return null;
        }
    }
}
