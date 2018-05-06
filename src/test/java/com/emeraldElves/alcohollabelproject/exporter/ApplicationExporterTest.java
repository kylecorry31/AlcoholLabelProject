package com.emeraldElves.alcohollabelproject.exporter;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.Data.ProductSource;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.database.CSVWriter;
import com.emeraldElves.alcohollabelproject.database.StructuredFileWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ApplicationExporterTest {

    private StringWriter writer;
    private StructuredFileWriter fileWriter;
    private ApplicationExporter exporter;
    private String header = String.join(",", ApplicationExporter.getCOLAHeader());

    @Before
    public void setup(){
        writer = new StringWriter();
        fileWriter = new CSVWriter(writer, ApplicationExporter.getCOLAHeader());
        exporter = new ApplicationExporter(fileWriter);
    }

    @Test
    public void testHeader(){
        assertEquals(header + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testSingleExport(){
        COLA cola = new COLA(180010010000006L, "brand", AlcoholType.BEER, "180006", ProductSource.DOMESTIC);
        cola.setStatus(ApplicationStatus.APPROVED);
        cola.setAlcoholContent(10);
        cola.setApplicantID(1);
        cola.setAssignedTo(2);
        cola.setFancifulName("fanciful");
        cola.setSubmissionDate(LocalDate.of(2018, 7, 1));
        cola.setExpirationDate(LocalDate.of(2020, 7, 1));
        cola.setApprovalDate(LocalDate.of(2019, 7, 1));
        cola.setLastUpdated(LocalDate.of(2019, 8, 1));
        exporter.export(cola);

        assertEquals(header + System.lineSeparator() + colaString(cola) + System.lineSeparator(), writer.toString());
    }

    @Test
    public void testExporting(){
        COLA cola = new COLA(180010010000006L, "brand", AlcoholType.BEER, "180006", ProductSource.DOMESTIC);
        cola.setStatus(ApplicationStatus.APPROVED);
        cola.setAlcoholContent(10);
        cola.setApplicantID(1);
        cola.setAssignedTo(2);
        cola.setFancifulName("fanciful");
        cola.setSubmissionDate(LocalDate.of(2018, 7, 1));
        cola.setExpirationDate(LocalDate.of(2020, 7, 1));
        cola.setApprovalDate(LocalDate.of(2019, 7, 1));
        cola.setLastUpdated(LocalDate.of(2019, 8, 1));
        exporter.export(cola);

        COLA cola2 = new COLA(180010010000008L, "brand2", AlcoholType.WINE, "180008", ProductSource.IMPORTED);
        cola2.setStatus(ApplicationStatus.SURRENDERED);
        cola2.setAlcoholContent(20);
        cola2.setApplicantID(2);
        cola2.setAssignedTo(23);
        cola2.setFancifulName("fanciful2");
        cola2.setSubmissionDate(LocalDate.of(2017, 7, 1));
        cola2.setExpirationDate(LocalDate.of(2021, 7, 1));
        cola2.setApprovalDate(LocalDate.of(2018, 7, 1));
        cola2.setLastUpdated(LocalDate.of(2018, 8, 1));
        cola2.setWinePH(7);
        cola2.setVintageYear(1990);
        cola2.setAppellation("Appellation");
        cola2.setVarietals("Varietals");
        exporter.export(cola2);

        assertEquals(header + System.lineSeparator() + colaString(cola) + System.lineSeparator() + colaString(cola2) + System.lineSeparator(), writer.toString());
    }

    private String colaString(COLA cola){
        String[] s = new String[]{
                String.valueOf(cola.getId()),
                cola.getBrandName(),
                cola.getType().toString(),
                cola.getSerialNumber(),
                cola.getOrigin().toString(),
                String.valueOf(cola.getAlcoholContent()),
                cola.getFancifulName(),
                cola.getSubmissionDate().toString(),
                cola.getStatus().toString(),
                cola.getApprovalDate().toString(),
                cola.getLabelImageFilename(),
                String.valueOf(cola.getApplicantID()),
                String.valueOf(cola.getAssignedTo()),
                String.valueOf(cola.getFormula()),
                String.valueOf(cola.getWinePH()),
                String.valueOf(cola.getVintageYear()),
                cola.getVarietals(),
                cola.getAppellation(),
                cola.getLastUpdated().toString(),
                cola.getExpirationDate().toString()
        };
        return String.join(",", s);
    }


}