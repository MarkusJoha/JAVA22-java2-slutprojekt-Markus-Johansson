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
    private Logger logger;

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
        frame.setSize(800, 400);

        JPanel mainPanel = new JPanel(new GridBagLayout());

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(progressBar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        producerLabel = new JLabel("" + controller.getProducerList());
        mainPanel.add(producerLabel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton removeProducerButton = new JButton("Remove Producer");
        JButton addProducerButton = new JButton("Add Producer");

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

        JButton quitButton = new JButton("Quit");
        textArea = new JTextArea(5, 50);
        textArea.setEditable(false);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(addProducerButton);
        buttonPanel.add(producerLabel);
        buttonPanel.add(removeProducerButton);
        buttonPanel.add(new JScrollPane(textArea));
        buttonPanel.add(quitButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
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