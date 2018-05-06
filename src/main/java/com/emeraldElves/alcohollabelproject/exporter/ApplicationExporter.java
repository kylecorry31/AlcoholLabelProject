package com.emeraldElves.alcohollabelproject.exporter;

import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.database.StructuredFileWriter;

public class ApplicationExporter {

    private StructuredFileWriter writer;

    public ApplicationExporter(StructuredFileWriter writer) {
        this.writer = writer;
    }

    public static String[] getCOLAHeader(){
        return new String[]{
                "ttb_id",
                "brand_name",
                "alcohol_type",
                "serial_num",
                "origin",
                "alcohol_content",
                "fanciful_name",
                "submission_date",
                "status",
                "approval_date",
                "label_image",
                "applicant_id",
                "ttb_agent_id",
                "formula",
                "wine_ph",
                "wine_vintage_year",
                "wine_varietals",
                "wine_appellation",
                "last_updated",
                "expiration_date"
        };
    }

    private String[] getCOLAData(COLA cola){
        return new String[]{
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
    }

    public void export(COLA cola){
        writer.write(getCOLAData(cola));
    }

}
