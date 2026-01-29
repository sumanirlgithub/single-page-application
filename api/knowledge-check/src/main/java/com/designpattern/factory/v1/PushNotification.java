package com.designpattern.factory.v1;

public class PushNotification implements Notification {

    public void send(String message) {
        System.out.println("Push notification sent: " + message);
    }

}
