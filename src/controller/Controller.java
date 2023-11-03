package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import model.Buffer;
import view.ProductionView;

public class Controller {
	private ProductionView prod;
	private LinkedList<Thread> producerList;
	private Random random;
	private Buffer buffer;

	public void start() {
		
		random = new Random();
		prod = new ProductionView(this);
		buffer = prod.getBuffer();
		SwingUtilities.invokeLater(() -> prod.createSwingLayout());

		timerUpdate();

		Producer producer = new Producer(buffer, 1000 + random.nextInt(9001));
		Thread producerThread = new Thread(producer);
		producerThread.start();
		producerList = new LinkedList<Thread>();
		producerList.add(producerThread);

		int randomNum = (int) (Math.random() * 14) + 3;
		for (int i = 0; i < randomNum; i++) {

			Consumer consumer = new Consumer(buffer);
			Thread consumerThread = new Thread(consumer);
			consumerThread.start();
		}

	}

	public void addProducer() {
		int timeInterval = 1000 + random.nextInt(9001);
		Producer producer = new Producer(buffer, timeInterval);
		Thread producerThread = new Thread(producer);
		producerThread.start();
		producerList.add(producerThread);

		String message = "Producer Added! Number of Producers: " + getProducerList()
				+ "\nThis Producer's Time Interval: " + timeInterval;
		prod.logMessage(message);
	}

	public int getProducerList() {
		return producerList.size();
	}

	public void removeProducer() {
		if (!producerList.isEmpty()) {
			Thread producerThread = producerList.removeFirst();
			producerThread.interrupt();
			System.out.println("Producers: " + producerList.size());
		}
	}

	private void timerUpdate() {
		int delay = 1000;
		Timer timer = new Timer(delay, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prod.updateProgressBar();
			}
		});
		timer.start();
		int delay2 = 10000;
		Timer timer2 = new Timer(delay2, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prod.updateProgressBar();
				prod.logMessage("Number of items in stock: " + buffer.getSize());
			}
		});
		timer2.start();
	}
}
