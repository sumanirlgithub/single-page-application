package com.designpattern.factory.v1;

public class EmailNotification implements Notification {

    public void send(String message) {
        System.out.println("Email sent: " + message);
    }
}
