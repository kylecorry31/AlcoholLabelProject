package com.emeraldElves.alcohollabelproject.database;

import java.io.Writer;

public class TSVWriter extends StructuredFileWriter {
    public TSVWriter(Writer writer, String[] headerCols, char quoteChar, char escapeChar) {
        super(writer, headerCols, '\t', quoteChar, escapeChar);
    }

    public TSVWriter(Writer writer, String[] headerCols, char quoteChar) {
        this(writer, headerCols, '\t', quoteChar);
    }

    public TSVWriter(Writer writer, String[] headerCols) {
        this(writer, headerCols, '\t');
    }
}
