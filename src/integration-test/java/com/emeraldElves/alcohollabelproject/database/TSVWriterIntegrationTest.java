package com.emeraldElves.alcohollabelproject.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class TSVWriterIntegrationTest {

    private TSVWriter fileWriter;
    private FileWriter writer;
    private String[] headerCols = new String[]{"name", "age", "pay"};
    private File file;

    @Before
    public void setup() throws IOException {
        file = new File("testing.tsv");
        writer = new FileWriter(file);
        fileWriter = new TSVWriter(writer, headerCols);
    }

    @After
    public void teardown(){
        file.delete();
    }

    @Test
    public void testWriteHeader() throws IOException {
        writer.close();
        assertEquals("name\tage\tpay" + System.lineSeparator(), readFileAsString(file));
    }

    @Test
    public void testWriteData() throws IOException {
        fileWriter.write(new String[]{"kyle", "19", "12.3"});
        fileWriter.write(new String[]{"ryan", "17", "0"});
        writer.close();
        assertEquals("name\tage\tpay" + System.lineSeparator() + "kyle\t19\t12.3" + System.lineSeparator() + "ryan\t17\t0" + System.lineSeparator(), readFileAsString(file));
    }

    @Test
    public void testNullData() throws IOException {
        fileWriter.write(null);
        writer.close();
        assertEquals("name\tage\tpay" + System.lineSeparator(), readFileAsString(file)) ;
    }

    @Test
    public void testNullHeader() throws IOException {
        writer = new FileWriter(file);
        fileWriter = new TSVWriter(writer, null);
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