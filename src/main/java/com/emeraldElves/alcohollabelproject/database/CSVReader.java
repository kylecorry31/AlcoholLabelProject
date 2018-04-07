package com.emeraldElves.alcohollabelproject.database;

import java.util.Scanner;

public class CSVReader extends StructuredFileReader {

    public CSVReader(Scanner csvData){
        super(csvData, ",");
    }

}
