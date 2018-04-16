package com.emeraldElves.alcohollabelproject.database;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;

public class StructuredFileWriter {

    private CSVWriter csvWriter;

    public StructuredFileWriter(Writer writer, String[] headerCols, char delimiter, char quoteChar, char escapeChar) {
        csvWriter = new CSVWriter(writer, delimiter, quoteChar, escapeChar, System.lineSeparator());
        write(headerCols);
    }

    public StructuredFileWriter(Writer writer, String[] headerCols, char delimiter, char quoteChar) {
        this(writer, headerCols, delimiter, quoteChar, '"');
    }

    public StructuredFileWriter(Writer writer, String[] headerCols, char delimiter) {
        this(writer, headerCols, delimiter, '\'', '"');
    }

    /**
     * Writes values to the writer with the specified delimiter.
     * @param values The values to write.
     */
    public void write(String[] values){
        if(values == null){
            return;
        }
        csvWriter.writeNext(values, false);
    }

    /**
     * Closes the writer.
     */
    public void close() {
        try {
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
