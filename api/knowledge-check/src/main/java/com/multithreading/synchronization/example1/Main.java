package com.multithreading.synchronization.example1;

/**
 *  let's explore how synchronization impacts data consistency when multiple threads are involved.
 *
 * When multiple threads access and modify shared variables, data inconsistencies and race conditions can occur if not managed properly.
 * In this task, you'll first run the code without synchronization and observe the result. Then, add the synchronized keyword to control access to the
 * shared variable.
 *
 * Here's what you need to do:
 *
 * Step 1: Run the code as it is multiple times without using synchronized keyword in SynchronizedCounter.java and observe the final count value.
 * With each iteration, you may notice inconsistencies due to the absence of synchronization.
 * Step 2: Add the synchronized keyword to the increment and getCount methods to control access.
 * Step 3: Run the code again and observe how the output changes.
 * These steps will help you see how synchronization ensures data accuracy in multithreaded scenarios.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        MyRunnableThread myRunnableThread = new MyRunnableThread();
        Thread t1 = new Thread(myRunnableThread);
        Thread t2 = new Thread(myRunnableThread);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Counter: " + myRunnableThread.counter.getCount());
    }
}
