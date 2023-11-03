package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static Logger instance;
    private PrintWriter writer;
    private static final String DEFAULT_LOG_FILE_PATH = "src/Files/log.txt";

    private Logger(String logFilePath) {
        try {
            writer = new PrintWriter(new FileWriter(logFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance() {
        return getInstance(DEFAULT_LOG_FILE_PATH);
    }

    public static Logger getInstance(String logFilePath) {
        if (instance == null) {
            instance = new Logger(logFilePath);
        }
        return instance;
    }

    public void log(String message) {
        String logEntry = "[" + getCurrentTimestamp() + "]: " + message;
        writer.println("Logger" + logEntry);
        writer.flush();
    }
    
    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}


