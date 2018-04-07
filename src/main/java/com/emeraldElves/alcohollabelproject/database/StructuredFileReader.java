package com.emeraldElves.alcohollabelproject.database;

import com.emeraldElves.alcohollabelproject.LogManager;

import java.util.*;

public class StructuredFileReader {

    private Map<String, List<String>> data;
    private int count;
    private String delimiter;

    public StructuredFileReader(Scanner dataStream, String delimiter) {
        if (delimiter == null) {
            delimiter = ",";
        }
        this.delimiter = delimiter;
        data = new HashMap<>();
        count = 0;
        parseData(dataStream);
    }

    public int getCount() {
        return count;
    }

    public String getString(String colName, int row) {
        return data.get(colName).get(row);
    }

    public int getInt(String colName, int row, int defaultVal) {
        try {
            return Integer.valueOf(getString(colName, row));
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public long getLong(String colName, int row, long defaultVal) {
        try {
            return Long.valueOf(getString(colName, row));
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public double getDouble(String colName, int row, double defaultVal) {
        try {
            return Double.valueOf(getString(colName, row));
        } catch (Exception e) {
            return defaultVal;
        }
    }

    private void parseData(Scanner dataStream) {
        if (dataStream == null || !dataStream.hasNextLine()) {
            return;
        }
        String header = dataStream.nextLine();
        String[] colNames = header.split(delimiter);
        for (String col : colNames) {
            data.put(col, new LinkedList<>());
        }

        count = 0;
        while (dataStream.hasNextLine()) {
            String line = dataStream.nextLine();
            String[] cols = line.split(delimiter);
            for (int i = 0; (i < cols.length) && (i < colNames.length); i++) {
                data.get(colNames[i]).add(cols[i]);
            }
            count++;
        }
    }

}
