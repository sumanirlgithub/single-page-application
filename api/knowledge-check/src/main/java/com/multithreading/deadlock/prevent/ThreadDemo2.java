package com.multithreading.deadlock.prevent;

public class ThreadDemo2 extends Thread {

    private Object object1;
    private Object object2;

    public ThreadDemo2(Object object1, Object object2) {
        this.object1 = object1;
        this.object2 = object2;
    }


    @Override
    public void run() {
        synchronized (object1) {
            try {
                System.out.println("Acquire lock on object1, waiting for object2");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object1) {
                System.out.println("Acquire lock on object1 and object2");
            }
        }
    }
}
