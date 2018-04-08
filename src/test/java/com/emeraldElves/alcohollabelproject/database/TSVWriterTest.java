package com.emeraldElves.alcohollabelproject.database;

import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class TSVWriterTest {

    private TSVWriter fileWriter;
    private StringWriter writer;
    private String[] headerCols = new String[]{"name", "age", "pay"};

    @Before
    public void setup(){
        writer = new StringWriter();
        fileWriter = new TSVWriter(writer, headerCols);
    }

    @Test
    public void testWriteHeader(){
        assertEquals("name\tage\tpay" + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testWriteData() {
        fileWriter.write(new String[]{"kyle", "19", "12.3"});
        assertEquals("name\tage\tpay" + System.lineSeparator() + "kyle\t19\t12.3" + System.lineSeparator(), writer.toString());
        fileWriter.write(new String[]{"ryan", "17", "0"});
        assertEquals("name\tage\tpay" + System.lineSeparator() + "kyle\t19\t12.3" + System.lineSeparator() + "ryan\t17\t0" + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testNullData(){
        fileWriter.write(null);
        assertEquals("name\tage\tpay" + System.lineSeparator(), writer.toString()) ;
    }

    @Test
    public void testNullHeader(){
        writer = new StringWriter();
        fileWriter = new TSVWriter(writer, null);
        assertEquals("", writer.toString());
    }

}