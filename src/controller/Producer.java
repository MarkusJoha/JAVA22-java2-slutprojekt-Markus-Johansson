package controller;

import java.util.Random;
import model.Buffer;
import model.Item;

public class Producer implements Runnable {
	Random random;
	Buffer buffer = null;
	boolean isRunning = true;
	int timeInterval;

	public Producer(Buffer buffer, int timeInterval) {
		this.buffer = buffer;
		this.timeInterval = timeInterval;
	}
	
	
	public int getTimeInterval() {
		return timeInterval;
	}


	@Override
	public void run() {
		System.out.println("Producer Time Interval: " + timeInterval);
		while (isRunning) {
			try {
				Thread.sleep(timeInterval);
				
				buffer.add(new Item("" + (int)(Math.random() * 100)));
			} catch (InterruptedException e) {
				isRunning = false;
				Thread.currentThread().interrupt();
			}
		}
	}

}
