package com.designpattern.factory.v1;

public class SMSNotification implements Notification {

    public void send(String message) {
        System.out.println("SMS sent: " + message);
    }

}
