package com.emeraldElves.alcohollabelproject.database;

import java.io.Reader;

public class CSVReader extends StructuredFileReader {

    public CSVReader(Reader csvData){
        super(csvData, ',');
    }

    public CSVReader(Reader csvData, char quoteChar, char escapeChar){
        super(csvData, ',', quoteChar, escapeChar);
    }

    public CSVReader(Reader csvData, char quoteChar){
        super(csvData, ',', quoteChar);
    }

}
