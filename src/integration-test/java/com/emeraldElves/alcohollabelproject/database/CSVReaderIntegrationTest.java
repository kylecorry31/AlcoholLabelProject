package com.emeraldElves.alcohollabelproject.database;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class CSVReaderIntegrationTest {


    @Test
    public void test(){
        CSVReader reader = new CSVReader(new Scanner(getClass().getResourceAsStream("/files/test.csv")));
        assertEquals(2, reader.getCount());
        assertEquals("kyle", reader.getString("name", 0));
        assertEquals("ryan", reader.getString("name", 1));
        assertEquals(19, reader.getInt("age", 0, 0));
        assertEquals(17, reader.getInt("age", 1, 0));
        assertEquals(12, reader.getDouble("pay", 0, 0), 0);
        assertEquals(0, reader.getDouble("pay", 1, 0), 0);


    }


}