package com.multithreading;

import java.util.concurrent.*;

public class ThreadExample4 {
	
	public static void main(String[] args) {		
		System.out.println("main thread start");
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
		Executor executor = new ThreadPoolExecutor(1, 10,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
				
		executor.execute(runnableTask);

		System.out.println("main thread end");
		
	}
}

