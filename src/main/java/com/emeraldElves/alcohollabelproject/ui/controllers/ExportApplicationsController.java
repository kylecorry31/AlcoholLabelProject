package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.CSVWriter;
import com.emeraldElves.alcohollabelproject.exporter.ApplicationExporter;
import com.emeraldElves.alcohollabelproject.exporter.PDFExporter;
import com.emeraldElves.alcohollabelproject.ui.DialogFileSelector;
import com.jfoenix.controls.JFXSnackbar;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ExportApplicationsController {

    private Pane pane;

    public ExportApplicationsController(Pane pane) {
        this.pane = pane;
    }

    public void export(List<COLA> applications){
        DialogFileSelector fileSelector = new DialogFileSelector();
        File file = fileSelector.saveFile("Comma Separated Values (CSV)", "*.csv");
        NotificationController notifier = new NotificationController();
        if (file == null){
            return;
        }
        try {
            FileWriter writer = new FileWriter(file);
            CSVWriter fileWriter = new CSVWriter(writer, ApplicationExporter.getCOLAHeader());
            ApplicationExporter applicationExporter = new ApplicationExporter(fileWriter);
            for (COLA cola: applications) {
                applicationExporter.export(cola);
            }
            fileWriter.close();
            notifier.notify(pane, "COLAs exported to " + file.getName());
        } catch (IOException e) {
            notifier.notify(pane, "Error exporting COLAs to " + file.getName(), "RETRY", () -> export(applications));
        }

    }

    public void exportPDF(COLA application, User user){
        DialogFileSelector fileSelector = new DialogFileSelector();
        File file = fileSelector.saveFile("PDF", "*.pdf");
        NotificationController notifier = new NotificationController();
        if (file == null){
            return;
        }
        try {
            PDFExporter pdfExporter = new PDFExporter(new File(getClass().getResource("/forms/cola.pdf").toURI()));
            pdfExporter.export(application, user, file);
            notifier.notify(pane, "COLA exported to " + file.getName());
        } catch (Exception e) {
            notifier.notify(pane, "Error exporting COLA to " + file.getName(), "RETRY", () -> exportPDF(application, user));
        }

    }

}
