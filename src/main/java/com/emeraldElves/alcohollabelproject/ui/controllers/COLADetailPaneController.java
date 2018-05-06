package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.data.COLA;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class COLADetailPaneController implements Initializable {

    @FXML
    private Label brandNameText, typeText, serialText, originText, fancifulText,
            alcoholContentText, formulaText, approvalDateText, submissionDateText, statusText;

    @FXML
    private Label winePHText, vintageYearText, appellationText, varietalsText;

    @FXML
    private HBox winePHHbox, vintageYearHbox, appellationBox, varietalsBox, approvalDateBox, submissionDateBox;

    private COLA cola;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        winePHHbox.managedProperty().bind(winePHHbox.visibleProperty());
        vintageYearHbox.managedProperty().bind(vintageYearHbox.visibleProperty());
        appellationBox.managedProperty().bind(appellationBox.visibleProperty());
        varietalsBox.managedProperty().bind(varietalsBox.visibleProperty());
        approvalDateBox.managedProperty().bind(approvalDateBox.visibleProperty());
        submissionDateBox.managedProperty().bind(submissionDateBox.visibleProperty());
    }

    public void setCOLA(COLA cola){
        this.cola = cola;
        updateUI();
    }

    private void updateUI(){
        if(cola == null)
            return;

        approvalDateBox.setVisible(true);
        submissionDateBox.setVisible(true);

        if(cola.getType() == AlcoholType.WINE){
            winePHHbox.setVisible(true);
            vintageYearHbox.setVisible(true);
            appellationBox.setVisible(true);
            varietalsBox.setVisible(true);

            if(cola.getWinePH() != -1.0){
                winePHText.setText(String.format("%.1f", cola.getWinePH()));
            } else {
                winePHHbox.setVisible(false);
            }

            if(cola.getVintageYear() != -1){
                vintageYearText.setText(String.format("%d", cola.getVintageYear()));
            } else {
                vintageYearHbox.setVisible(false);
            }

            if (!cola.getAppellation().isEmpty()){
                appellationText.setText(cola.getAppellation());
            } else {
                appellationBox.setVisible(false);
            }

            if (!cola.getVarietals().isEmpty()){
                varietalsText.setText(cola.getVarietals());
            } else {
                varietalsBox.setVisible(false);
            }
        } else {
            winePHHbox.setVisible(false);
            vintageYearHbox.setVisible(false);
            appellationBox.setVisible(false);
            varietalsBox.setVisible(false);
        }

        brandNameText.setText(cola.getBrandName());
        fancifulText.setText(cola.getFancifulName());
        alcoholContentText.setText(String.format("%.1f%% (%.1f Proof)", cola.getAlcoholContent(), cola.getProof()));
        serialText.setText(cola.getSerialNumber());
        originText.setText(cola.getOrigin().getDisplayName());
        typeText.setText(cola.getType().getDisplayName());

        if(!cola.getApprovalDate().equals(COLA.NULL_DATE)) {
            approvalDateText.setText(cola.getApprovalDate().toString());
        } else {
            approvalDateBox.setVisible(false);
        }

        if(!cola.getSubmissionDate().equals(COLA.NULL_DATE)) {
            submissionDateText.setText(cola.getSubmissionDate().toString());
        } else {
            submissionDateBox.setVisible(false);
        }

        statusText.setText(cola.getStatus().getMessage());
        if(cola.getFormula() == -1){
            formulaText.setText("N/A");
        } else {
            formulaText.setText(String.valueOf(cola.getFormula()));
        }
    }

}
