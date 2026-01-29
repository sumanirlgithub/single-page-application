package com.multithreading.deadlock;

public class TestDeadlock {

    public static void main(String[] args) {

        Object object1 = new Object();
        Object object2 = new Object();

        ThreadDemo1 threadDemo1 = new ThreadDemo1(object1, object2);
        threadDemo1.start();
        ThreadDemo2 threadDemo2 = new ThreadDemo2(object1, object2);
        threadDemo2.start();

    }

}