package com.emeraldElves.alcohollabelproject.database;

import java.io.Writer;

public class CSVWriter extends StructuredFileWriter {
    public CSVWriter(Writer writer, String[] headerCols, char quoteChar, char escapeChar) {
        super(writer, headerCols, ',', quoteChar, escapeChar);
    }

    public CSVWriter(Writer writer, String[] headerCols, char quoteChar) {
        this(writer, headerCols, ',', quoteChar);
    }

    public CSVWriter(Writer writer, String[] headerCols) {
        this(writer, headerCols, ',');
    }}
