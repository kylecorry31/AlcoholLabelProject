package com.emeraldElves.alcohollabelproject.UserInterface;

import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class PagePrinter {

    public static void print(Scene scene){
        PrinterJob job = PrinterJob.createPrinterJob();
        if(job != null){
            Stage stage = new Stage();
            job.showPrintDialog(stage);
            PageLayout pageLayout = job.getPrinter().getDefaultPageLayout();
            double scaleX = pageLayout.getPrintableWidth() / scene.getWidth();
            double scaleY = pageLayout.getPrintableHeight() / scene.getHeight();
            double minimumScale = Math.min(scaleX, scaleY);
            Scale scale = new Scale(minimumScale, minimumScale);
            scene.getRoot().getTransforms().add(scale);
            job.printPage(scene.getRoot());
            job.endJob();
            scene.getRoot().getTransforms().add(new Scale(1/minimumScale, 1/minimumScale));
        }
    }

}
