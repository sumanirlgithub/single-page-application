package com.multithreading.sleepjoin.example1;

// TODO: Implement RedLight class implementing Runnable
// TODO: In run(), print "Red Light ON", then sleep for 5 seconds
// TODO: After sleep, print "Red Light OFF"
// TODO: Handle InterruptedException with try-catch

class RedLight implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("Red Light ON");
            Thread.sleep(5000);
            System.out.println("Red Light OFF");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
}
