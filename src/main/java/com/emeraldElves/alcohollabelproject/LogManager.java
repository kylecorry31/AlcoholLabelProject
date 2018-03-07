package com.emeraldElves.alcohollabelproject;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by Tarek on 4/9/2017.
 */
public class LogManager {

    private static LogManager _Log;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy.HH.mm.ss");

    private LogManager() {}
    private static class LogManagerHolder {
        private static final LogManager instance = new LogManager();
    }
    public static LogManager getInstance(){
        return LogManagerHolder.instance;
    }

    private void writeFile (String text) {
        try {
            FileUtils.writeStringToFile(new File("Log.txt"), text, true);
        }
        catch (IOException e) {
            System.err.println("Unable to create log file");
        }
    }
    public void log(String classTag, String specialMessage) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String line = String.format("[%s] %s: %s", timestamp.toString(), classTag, specialMessage);

        System.out.println(line);

        writeFile(line + "\n");
    }

}
