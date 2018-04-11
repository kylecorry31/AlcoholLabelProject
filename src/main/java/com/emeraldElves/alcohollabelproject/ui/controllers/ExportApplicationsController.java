package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.database.CSVWriter;
import com.emeraldElves.alcohollabelproject.exporter.ApplicationExporter;
import com.emeraldElves.alcohollabelproject.ui.DialogFileSelector;
import com.jfoenix.controls.JFXSnackbar;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportApplicationsController {

    private Pane pane;

    public ExportApplicationsController(Pane pane) {
        this.pane = pane;
    }

    public void export(List<COLA> applications){
        DialogFileSelector fileSelector = new DialogFileSelector();
        File file = fileSelector.saveFile("Comma Separated Values (CSV)", "*.csv");
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
            JFXSnackbar snackbar = new JFXSnackbar(pane);
            snackbar.show("COLAs exported to " + file.getName(), 3000);
        } catch (IOException e) {
            JFXSnackbar snackbar = new JFXSnackbar(pane);
            snackbar.show("Error exporting COLAs to " + file.getName(), "RETRY", actionEvent -> {
                export(applications);
                snackbar.unregisterSnackbarContainer(pane);
            });
        }

    }

}
