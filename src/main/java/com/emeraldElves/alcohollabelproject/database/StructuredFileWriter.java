package com.emeraldElves.alcohollabelproject.database;

import java.io.IOException;
import java.io.Writer;

public class StructuredFileWriter {

    private String delimiter;
    private Writer writer;

    public StructuredFileWriter(Writer writer, String[] headerCols, String delimiter) {
        if (delimiter == null){
            delimiter = ",";
        }
        this.delimiter = delimiter;
        this.writer = writer;
        write(headerCols);
    }

    /**
     * Writes values to the writer with the specified delimiter.
     * @param values The values to write.
     */
    public void write(String[] values){
        if(values == null || writer == null){
            return;
        }

        try {
            writer.write(String.join(delimiter, values) + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the writer.
     */
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
