package com.zvoykish.zevo.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 00:59:32
 */
public class Logger {
    private PrintWriter writer;
    private static Logger instance;
    private static Logger csvInstance;
    private String filename;
    private boolean csvResults;

    private Logger(boolean csvResults) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd__HHmm_ss").format(new Date());
            filename = "Log_" + timeStamp + (csvResults ? ".csv" : ".log");
            this.csvResults = csvResults;
            writer = new PrintWriter(filename);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance() {
        return getInstance(false);
    }

    public static Logger getInstance(boolean csvResults) {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger(false);
                    csvInstance = new Logger(true);
                }
            }
        }
        return csvResults ? csvInstance : instance;
    }

    public void log(String msg) {
        writer.println((csvResults ? "" : new Date().toString() + " - ") + msg);
        writer.flush();
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }

    public String getFilename() {
        return filename;
    }
}
