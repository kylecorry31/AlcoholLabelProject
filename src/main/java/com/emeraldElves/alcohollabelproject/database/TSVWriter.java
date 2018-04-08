package com.emeraldElves.alcohollabelproject.database;

import java.io.Writer;

public class TSVWriter extends StructuredFileWriter {
    public TSVWriter(Writer writer, String[] headerCols) {
        super(writer, headerCols, "\t");
    }
}
