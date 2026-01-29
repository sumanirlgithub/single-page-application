package com.multithreading;

public class MyThread extends Thread {

	@Override
	public void run() {
		//System.out.println("Hello thread");
		for (int i = 1; i<= 100; i++) {
			System.out.println(i);
		}
	}
	
}