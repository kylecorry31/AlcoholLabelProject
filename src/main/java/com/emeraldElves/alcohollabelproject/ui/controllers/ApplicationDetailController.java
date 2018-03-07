package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.ui.ImageUtils;
import com.emeraldElves.alcohollabelproject.ui.PagePrinter;
import com.emeraldElves.alcohollabelproject.data.COLA;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ApplicationDetailController {

    @FXML
    Label brandName;

    @FXML
    Label serialNum;

    @FXML
    Label fancifulName;

    @FXML
    Label alcoholType;

    @FXML
    Label submissionDate;

    @FXML
    Label content;

    @FXML
    Label origin;

    @FXML
    ImageView labelView;

    @FXML
    Label status;

    @FXML
    Label email;

    @FXML
    Label phone;

    @FXML
    Label address;

    @FXML
    Label ttbID;


    private COLA alcohol;

    public void setAlcohol(COLA alcohol){
        this.alcohol = alcohol;
        updateUI();
    }

    private void updateUI(){
        if(alcohol == null)
            return;

        brandName.setText(alcohol.getBrandName());
        fancifulName.setText(alcohol.getFancifulName());
        content.setText(String.format("%.1f%% (%.1f Proof)", alcohol.getAlcoholContent(), alcohol.getAlcoholContent() * 2));
        serialNum.setText(alcohol.getSerialNumber());
        origin.setText(alcohol.getOrigin().toString());
        alcoholType.setText(alcohol.getType().getDisplayName());
        submissionDate.setText(alcohol.getApprovalDate().toString());
        ttbID.setText("TTB ID #" + String.valueOf(alcohol.getId()));
        labelView.setImage(alcohol.getLabelImage().display());
        ImageUtils.centerImage(labelView);
        status.setText(alcohol.getStatus().getMessage().toUpperCase());
        if(alcohol.getStatus() == ApplicationStatus.REJECTED){
            status.setStyle(status.getStyle() + "-fx-text-fill: red;");
        }
    }

    public void export(){

    }

    public void printPage(){
        PagePrinter.print(brandName.getScene());
    }

}
