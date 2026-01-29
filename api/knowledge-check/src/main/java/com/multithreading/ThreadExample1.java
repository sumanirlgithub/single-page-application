package com.multithreading;

public class ThreadExample1 {
	
	public static void main(String[] args) {
		
		System.out.println("main thread start");
		try { 
				MyThread t1 = new MyThread();
				t1.start();
				t1.join();
				MyThread t2 = new MyThread();
				t2.start();		
				t2.join();
		} catch(InterruptedException e) {
			
		}
		System.out.println("main thread end");
		
	}
}