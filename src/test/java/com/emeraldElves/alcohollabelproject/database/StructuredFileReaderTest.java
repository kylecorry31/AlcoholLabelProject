package com.emeraldElves.alcohollabelproject.database;

import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class StructuredFileReaderTest {

    private String data = "name:age:pay\nKyle:19:12\n\"Ryan:test\":17:0";

    @Test
    public void test(){
        StructuredFileReader reader = new StructuredFileReader(new StringReader(data), ':');
        assertEquals(2, reader.getCount());
        assertEquals("Kyle", reader.getString("name", 0));
        assertEquals("Ryan:test", reader.getString("name", 1));
        assertEquals(19, reader.getInt("age", 0, 0));
        assertEquals(17, reader.getInt("age", 1, 0));
        assertEquals(12, reader.getDouble("pay", 0, 0), 0);
        assertEquals(0, reader.getDouble("pay", 1, 0), 0);


    }


}