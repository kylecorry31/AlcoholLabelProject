package com.emeraldElves.alcohollabelproject.database;

import org.junit.Before;
import org.junit.Test;


import java.io.StringWriter;

import static org.junit.Assert.*;

public class StructuredFileWriterTest {

    private StructuredFileWriter fileWriter;
    private StringWriter writer;
    private String[] headerCols = new String[]{"name", "age", "pay"};

    @Before
    public void setup(){
        writer = new StringWriter();
        fileWriter = new StructuredFileWriter(writer, headerCols, "::");
    }

    @Test
    public void testWriteHeader(){
        assertEquals("name::age::pay" + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testWriteData() {
        fileWriter.write(new String[]{"kyle", "19", "12.3"});
        assertEquals("name::age::pay" + System.lineSeparator() + "kyle::19::12.3" + System.lineSeparator(), writer.toString());
        fileWriter.write(new String[]{"ryan", "17", "0"});
        assertEquals("name::age::pay" + System.lineSeparator() + "kyle::19::12.3" + System.lineSeparator() + "ryan::17::0" + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testNullData(){
        fileWriter.write(null);
        assertEquals("name::age::pay" + System.lineSeparator(), writer.toString()) ;
    }

    @Test
    public void testNullHeader(){
        writer = new StringWriter();
        fileWriter = new StructuredFileWriter(writer, null, ",");
        assertEquals("", writer.toString());
    }

    @Test
    public void testNullDelimiter(){
        writer = new StringWriter();
        fileWriter = new StructuredFileWriter(writer, headerCols, null);
        assertEquals("name,age,pay" + System.lineSeparator(), writer.toString());
    }

}