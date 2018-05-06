package com.emeraldElves.alcohollabelproject.exporter;

import com.emeraldElves.alcohollabelproject.Data.*;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.data.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class PDFExporterTest {

    private File input, output;
    private COLA cola;
    private User user;

    @Before
    public void setup() throws URISyntaxException {
        input = new File(getClass().getResource("/forms/cola.pdf").toURI());
        output = new File("test.pdf");
        cola = new COLA(1, "Test'Brand", AlcoholType.WINE, "180001", ProductSource.DOMESTIC);
        cola.setAlcoholContent(10);
        cola.setFancifulName("TestFanciful");
        cola.setVarietals("Varietals");
        cola.setAppellation("Appellation");

        user = new User("test@test.com", "Test", "pass", UserType.APPLICANT);
        user.setPermitNo("12345");
        user.setPhoneNumber(new PhoneNumber("5555555555"));
        user.setAddress("Worcester, MA, USA");
    }

    @Test
    public void testExport() {
        PDFExporter exporter = new PDFExporter(input);
        exporter.export(cola, user, output);
    }

    @After
    public void cleanup(){
        output.delete();
    }

}