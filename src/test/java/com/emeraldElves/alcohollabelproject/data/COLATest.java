package com.emeraldElves.alcohollabelproject.data;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.Data.ProductSource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class COLATest {

    private COLA cola;

    @Before
    public void setup(){
        cola = new COLA(1, "Brand", AlcoholType.BEER, "123456", ProductSource.DOMESTIC);
    }

    @Test
    public void testProof(){
        cola.setAlcoholContent(0);
        assertEquals(0, cola.getProof(), 0.0001);
        cola.setAlcoholContent(50);
        assertEquals(100, cola.getProof(), 0.0001);
        cola.setAlcoholContent(100);
        assertEquals(200, cola.getProof(), 0.0001);
        cola.setProof(10);
        assertEquals(10, cola.getProof(), 0.0001);
        assertEquals(5, cola.getAlcoholContent(), 0.0001);
    }

}