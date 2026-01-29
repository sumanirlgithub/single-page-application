package com.multithreading;

public class ThreadExample2 {
	
	public static void main(String[] args) {
		
		System.out.println("main thread start");
		try { 
				Runnable runnable = new SumanThread();
				Thread t1 =  new Thread(runnable);
				t1.start();
				t1.join();
				Thread t2 =  new Thread(runnable);
				t2.start();		
				t2.join();
		} catch(InterruptedException e) {
			
		}
		System.out.println("main thread end");
		
	}
}