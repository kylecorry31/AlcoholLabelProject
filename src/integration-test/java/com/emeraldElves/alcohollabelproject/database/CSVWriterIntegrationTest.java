package com.emeraldElves.alcohollabelproject.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class CSVWriterIntegrationTest {

    private CSVWriter fileWriter;
    private FileWriter writer;
    private String[] headerCols = new String[]{"name", "age", "pay"};
    private File file;

    @Before
    public void setup() throws IOException {
        file = new File("testing.csv");
        writer = new FileWriter(file);
        fileWriter = new CSVWriter(writer, headerCols);
    }

    @After
    public void teardown(){
        file.delete();
    }

    @Test
    public void testWriteHeader() throws IOException {
        writer.close();
        assertEquals("name,age,pay" + System.lineSeparator(), readFileAsString(file));
    }

    @Test
    public void testWriteData() throws IOException {
        fileWriter.write(new String[]{"kyle", "19", "12.3"});
        fileWriter.write(new String[]{"ryan", "17", "0"});
        writer.close();
        assertEquals("name,age,pay" + System.lineSeparator() + "kyle,19,12.3" + System.lineSeparator() + "ryan,17,0" + System.lineSeparator(), readFileAsString(file));
    }

    @Test
    public void testNullData() throws IOException {
        fileWriter.write(null);
        writer.close();
        assertEquals("name,age,pay" + System.lineSeparator(), readFileAsString(file)) ;
    }

    @Test
    public void testNullHeader() throws IOException {
        writer = new FileWriter(file);
        fileWriter = new CSVWriter(writer, null);
        writer.close();
        assertEquals("", readFileAsString(file));
    }

    private String readFileAsString(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNextLine()){
            sb.append(scanner.nextLine()).append(System.lineSeparator());
        }
        return sb.toString();
    }

}