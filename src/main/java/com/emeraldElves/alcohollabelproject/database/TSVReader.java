package com.emeraldElves.alcohollabelproject.database;

import java.util.Scanner;

public class TSVReader extends StructuredFileReader {

    public TSVReader(Scanner tsvData){
        super(tsvData, "\t");
    }

}
