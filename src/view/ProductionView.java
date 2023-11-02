package view;

import javax.swing.*;
import controller.Controller;
import logger.Logger;
import model.Buffer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ProductionView {
	private Buffer buffer;
    private JFrame frame;
    private JProgressBar progressBar;
    private Controller controller;
	private JLabel producerLabel;
    private JTextArea textArea;
    Logger logger;

    public ProductionView(Controller controller) {
    	this.controller = controller;
    	progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
    	buffer = new Buffer(progressBar);
    	logger = Logger.getInstance();
    }
    
    public void createSwingLayout() {  
        frame = new JFrame("Factory Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        producerLabel = new JLabel("" + controller.getProducerList());
        JButton removeProducerButton = new JButton("Remove Producer");
        JButton addProducerButton = new JButton("Add Producer");
        textArea = new JTextArea(5, 50);
        textArea.setEditable(false);
        JButton quitButton = new JButton("Quit");

        removeProducerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                	controller.removeProducer();
                    updateProducerLabel();
                    logMessage("Producer Removed! Number of Producers: " + controller.getProducerList());
            }
        });

        addProducerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	controller.addProducer();
                updateProducerLabel();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(removeProducerButton);
        buttonPanel.add(producerLabel);
        buttonPanel.add(addProducerButton);
        buttonPanel.add(new JScrollPane(textArea));
        buttonPanel.add(quitButton);

        frame.add(progressBar, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    public Buffer getBuffer() {
		return buffer;
	}

	private void updateProducerLabel() {
        producerLabel.setText("" + controller.getProducerList());
    }
	
	public void updateProgressBar() {
		int size = buffer.getSize();
		progressBar.setValue(size);
		progressBar.setForeground(getColorForPercentage(size));
		if (size <= 10) {
			logMessage("LOW PRODUCTS!");
		} else if (size >= 90) {
			logMessage("MANY PRODUCTS!");
		}
	}
	
	private Color getColorForPercentage(double percentage) {
		if (percentage >= 90) {
			return Color.GREEN;
		} else if(percentage <= 10) {
			return Color.RED;
		} else {
			return Color.CYAN;
		}
	}
	
	public void logMessage(String message) {
	    String currentText = textArea.getText();
	    String newMessage = "[" + new Date() + "]: " + message + "\n";
	    textArea.setText(currentText + newMessage);
	    logger.log(message);
	    textArea.setCaretPosition(textArea.getDocument().getLength());
	}

}