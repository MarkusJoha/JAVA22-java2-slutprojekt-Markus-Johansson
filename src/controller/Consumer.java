package controller;

import java.util.Random;
import model.Buffer;

public class Consumer implements Runnable {
	private Random random;
	private Buffer buffer = null;
	private boolean isRunning = true;

	public Consumer(Buffer buffer) {
		this.buffer = buffer;
		this.random = new Random();
	}

	@Override
	public void run() {
		int timeInterval = 1000 + random.nextInt(9001);
		System.out.println("Consumer Time Interval: " + timeInterval);
		while (isRunning) {
			try {
				Thread.sleep(timeInterval);
				System.out.println("Consumed: " + buffer.remove());
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}

}
