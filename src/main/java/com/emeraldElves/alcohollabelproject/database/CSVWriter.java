package com.emeraldElves.alcohollabelproject.database;

import java.io.Writer;

public class CSVWriter extends StructuredFileWriter {
    public CSVWriter(Writer writer, String[] headerCols) {
        super(writer, headerCols, ",");
    }
}
