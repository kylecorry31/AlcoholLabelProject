package com.emeraldElves.alcohollabelproject;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by Tarek on 4/9/2017.
 */
public class LogManager {

    private boolean shouldWrite = true;
    private boolean shouldPrint = true;

    private static final String LOG_FILE = "log.txt";

    private LogManager() {}

    private static class LogManagerHolder {
        private static final LogManager instance = new LogManager();
    }

    public static LogManager getInstance(){
        return LogManagerHolder.instance;
    }

    private void writeFile (String text) {
        try {
            FileUtils.writeStringToFile(new File(LOG_FILE), text, true);
        }
        catch (IOException e) {
            System.err.println("Unable to create log file");
        }
    }

    public void setShouldWrite(boolean shouldWrite){
        this.shouldWrite = shouldWrite;
    }

    public void setShouldPrint(boolean shouldPrint){
        this.shouldPrint = shouldPrint;
    }

    public void log(String classTag, String specialMessage) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String line = String.format("[%s] %s: %s", timestamp.toString(), classTag, specialMessage);

        if(shouldPrint)
            System.out.println(line);

        if(shouldWrite)
            writeFile(line + "\n");
    }

}
