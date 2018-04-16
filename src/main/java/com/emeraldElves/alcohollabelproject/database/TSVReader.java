package com.emeraldElves.alcohollabelproject.database;

import java.io.Reader;

public class TSVReader extends StructuredFileReader {

    public TSVReader(Reader tsvData, char quoteChar, char escapeChar){
        super(tsvData, '\t', quoteChar, escapeChar);
    }

    public TSVReader(Reader tsvData, char quoteChar){
        super(tsvData, '\t', quoteChar);
    }

    public TSVReader(Reader tsvData){
        super(tsvData, '\t');
    }

}
