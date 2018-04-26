package com.emeraldElves.alcohollabelproject.exporter;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.data.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFExporter {

    private PDDocument template;
    private File output;

    public PDFExporter(File template){
        try {
            this.template = PDDocument.load(template);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void export(COLA cola, User user, File output){
        if(template == null)
            return;

        template.setAllSecurityToBeRemoved(true);
        PDAcroForm form = template.getDocumentCatalog().getAcroForm();

        if(form != null){
            try {
                form.setXFA(null);
                if (cola.getApplicantID() != -1) {
                    form.getField("1").setValue(String.valueOf(cola.getApplicantID()));
                }
                form.getField("2").setValue(String.valueOf(user.getPermitNo()));
                form.getField("YEAR 1").setValue(String.valueOf(cola.getSerialNumber().charAt(0)));
                form.getField("YEAR 2").setValue(String.valueOf(cola.getSerialNumber().charAt(1)));
                form.getField("SERIAL NUMBER 1").setValue(String.valueOf(cola.getSerialNumber().charAt(2)));
                form.getField("SERIAL NUMBER 2").setValue(String.valueOf(cola.getSerialNumber().charAt(3)));
                form.getField("SERIAL NUMBER 3").setValue(String.valueOf(cola.getSerialNumber().charAt(4)));
                form.getField("SERIAL NUMBER 4").setValue(String.valueOf(cola.getSerialNumber().charAt(5)));

                String imported;
                switch (cola.getOrigin()){
                    case DOMESTIC:
                        imported = "Domes";
                        break;
                    default:
                        imported = "Import";
                }
                form.getField("Check Box34").setValue(imported);

                String type;
                switch (cola.getType()){
                    case WINE:
                        type = "Wine";
                        break;
                    case DISTILLEDSPIRITS:
                        type = "Spirits";
                        break;
                    default:
                        type = "Malt";
                }

                form.getField("Check Box22").setValue(type);

                form.getField("6").setValue(cola.getBrandName());

                form.getField("7").setValue(cola.getFancifulName());

                form.getField("9").setValue(cola.getFormula() != -1 ? String.valueOf(cola.getFormula()) : "N/A");

                form.getField("12").setValue(user.getPhoneNumber().getFormattedNumber()); // TODO: didn't show up

                form.getField("13").setValue(user.getEmail().getEmailAddress()); // TODO: didn't show up


                form.getField("16").setValue(cola.getSubmissionDate().toString());

                if(cola.getStatus() == ApplicationStatus.APPROVED) {
                    form.getField("19").setValue(cola.getApprovalDate().toString()); // TODO: blank if not approved
                }
//                form.getField("20").setValue(String.valueOf(cola.getAssignedTo())); // TODO: Get agent name

                form.getField("8").setValue(user.getName() + ", " + user.getAddress());

                form.getField("18").setValue(user.getName());

                if (cola.getType() == AlcoholType.WINE){
//                    form.getField("11").setValue(String.valueOf(cola.getVintageYear()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            template.save(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: Reset form
    }

    /**
     * Fields:
     *
     * Check Box22      - 5 Wine, Spirits, or Malt
     * Check Box109     - 14a
     * Text5            - Qualifications
     * Text24
     * Text25
     * Text22
     * Text26
     * Text28
     * Text29
     * Text33
     * Check Box34      - 3 Domes, Import
     * Check Box300
     * Check Box301     - 14c
     * Check Box302
     * Text32
     * Text35
     * Text36
     * Text23
     * 9
     * 11
     * 12
     * 13
     * 7
     * 6
     * 10
     * 8
     * 8a
     * 1
     * 2
     * YEAR 1
     * YEAR 2
     * SERIAL NUMBER 1
     * SERIAL NUMBER 2
     * SERIAL NUMBER 3
     * SERIAL NUMBER 4
     * 14 b (Fill in State abbreviation)
     * (Fill in amount)
     * TTB ID
     * 15
     * 16
     * 18
     * 19
     * FOR TTB USE ONLY - QUALIFICATIONS
     * FOR TTB USE ONLY - EXPIRATION DATE (If any)
     */


}
