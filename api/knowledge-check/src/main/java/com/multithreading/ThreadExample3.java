package com.multithreading;

public class ThreadExample3 {
	
	public static void main(String[] args) {
		
		System.out.println("main thread start");
		try {

				Runnable runnableTask = () -> {
					try {
						for (int i = 1; i<= 100; i++) {
							System.out.println(i);
							Thread.sleep(1000);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				};		
				
				Thread t1 =  new Thread(runnableTask);
				t1.start();
				t1.join();
				Thread t2 =  new Thread(runnableTask);
				t2.start();		
				t2.join();
		} catch(InterruptedException e) {
			
		}
		System.out.println("main thread end");
		
	}
}

/*
wait() and sleep() methods in Java, are used to pause the execution of a particular thread in a multi threaded environment. 
Whenever a thread calls wait() method, it releases the lock or monitor it holds and when it calls sleep() method, 
it doesn’t release the lock or monitor it holds. This is the main difference between wait() and sleep() methods.

*/