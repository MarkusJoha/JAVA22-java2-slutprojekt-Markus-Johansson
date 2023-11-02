package model;

import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JProgressBar;

public class Buffer {
	private JProgressBar progressBar;
	
	Queue<Item> buffer = new LinkedList<Item>();
	
	public Buffer(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	
	
	public synchronized void add(Item item) {
		buffer.add(item);
		notify();
		System.out.println(buffer);
		progressBar.setValue(buffer.size());
	}
	
	public synchronized Item remove() {
		if(buffer.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		progressBar.setValue(progressBar.getValue() - 1);
		return buffer.remove();
	}
	
	public int getSize() {
		return buffer.size();
	}
	
	
}
