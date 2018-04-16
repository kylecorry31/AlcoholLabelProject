package com.emeraldElves.alcohollabelproject.database;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class StructuredFileReader {

    private Map<String, List<String>> data;
    private int count;
    private CSVReader reader;

    public StructuredFileReader(Reader dataStream, char delimiter, char quoteChar, char escapeChar) {
        data = new HashMap<>();
        count = 0;
        reader = new CSVReaderBuilder(dataStream)
                .withCSVParser(new CSVParserBuilder().withSeparator(delimiter).withQuoteChar(quoteChar).withEscapeChar(escapeChar).build())
                .build();
        parseData();
    }

    public StructuredFileReader(Reader dataStream, char delimiter, char quoteChar){
        this(dataStream, delimiter, quoteChar, '\'');
    }

    public StructuredFileReader(Reader dataStream, char delimiter){
        this(dataStream, delimiter, '"', '\'');
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

    private void parseData() {
        try {
            String[] colNames = reader.readNext();
            for (String col : colNames) {
                data.put(col, new LinkedList<>());
            }

            count = 0;
            List<String[]> lines = reader.readAll();
            for (String[] cols : lines) {
                for (int j = 0; (j < cols.length) && (j < colNames.length); j++) {
                    data.get(colNames[j]).add(cols[j]);
                }
                count++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
