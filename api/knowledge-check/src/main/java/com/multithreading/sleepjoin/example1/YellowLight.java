package com.multithreading.sleepjoin.example1;

// TODO: Implement YellowLight class implementing Runnable
// TODO: In run(), print "Yellow Light ON", then sleep for 2 seconds
// TODO: After sleep, print "Yellow Light OFF"
// TODO: Handle InterruptedException with try-catch

class YellowLight implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("Yellow Light ON");
            Thread.sleep(2000);
            System.out.println("Yellow Light OFF");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
}