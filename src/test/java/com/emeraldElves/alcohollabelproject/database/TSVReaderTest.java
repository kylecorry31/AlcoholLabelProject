package com.emeraldElves.alcohollabelproject.database;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class TSVReaderTest {

    private String tsv = "name\tage\tpay\nKyle\t19\t12\nRyan\t17\t0";

    @Test
    public void test(){
        TSVReader reader = new TSVReader(new Scanner(tsv));
        assertEquals(2, reader.getCount());
        assertEquals("Kyle", reader.getString("name", 0));
        assertEquals("Ryan", reader.getString("name", 1));
        assertEquals(19, reader.getInt("age", 0, 0));
        assertEquals(17, reader.getInt("age", 1, 0));
        assertEquals(12, reader.getDouble("pay", 0, 0), 0);
        assertEquals(0, reader.getDouble("pay", 1, 0), 0);


    }


}