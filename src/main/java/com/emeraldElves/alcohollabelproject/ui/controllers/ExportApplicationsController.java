package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.database.CSVWriter;
import com.emeraldElves.alcohollabelproject.exporter.ApplicationExporter;
import com.emeraldElves.alcohollabelproject.ui.DialogFileSelector;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportApplicationsController {

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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("COLA Exporter");
            alert.setHeaderText(null);
            alert.setContentText("COLAs have been exported to " + file.getName());

            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("COLA Exporter");
            alert.setHeaderText(null);
            alert.setContentText("Error exporting COLAs to " + file.getName());

            alert.showAndWait();
        }

    }

}
