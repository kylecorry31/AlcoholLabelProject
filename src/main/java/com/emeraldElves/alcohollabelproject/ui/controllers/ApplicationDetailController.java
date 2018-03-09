package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.Storage;
import com.emeraldElves.alcohollabelproject.ui.ImageUtils;
import com.emeraldElves.alcohollabelproject.ui.PagePrinter;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationDetailController implements Initializable {

    @FXML
    private Label brandNameText, typeText, serialText, originText, fancifulText, alcoholContentText, formulaText, approvalDateText;

    @FXML
    private Label winePHText, vintageYearText;

    @FXML
    private HBox winePHHbox, vintageYearHbox;

    @FXML
    private ImageView labelImage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label userNameText, emailText, phoneText, companyText, permitText, addressText;

    @FXML
    private Label ttbID;


    private COLA alcohol;
    private String searchTerm;

    public void setAlcohol(COLA alcohol){
        this.alcohol = alcohol;
        updateUI();
    }

    public void setSearchTerm(String searchTerm){
        this.searchTerm = searchTerm;
    }

    private void updateUI(){
        if(alcohol == null)
            return;

        if(alcohol.getType() == AlcoholType.WINE){
            winePHHbox.setVisible(true);
            vintageYearHbox.setVisible(true);

            if(alcohol.getWinePH() != -1.0){
                winePHText.setText(String.format("%.1f", alcohol.getWinePH()));
            } else {
                winePHHbox.setVisible(false);
            }

            if(alcohol.getVintageYear() != -1){
                vintageYearText.setText(String.format("%d", alcohol.getVintageYear()));
            } else {
                vintageYearHbox.setVisible(false);
            }
        } else {
            winePHHbox.setVisible(false);
            vintageYearHbox.setVisible(false);
        }

        brandNameText.setText(alcohol.getBrandName());
        fancifulText.setText(alcohol.getFancifulName());
        alcoholContentText.setText(String.format("%.1f%% (%.1f Proof)", alcohol.getAlcoholContent(), alcohol.getAlcoholContent() * 2));
        serialText.setText(alcohol.getSerialNumber());
        originText.setText(alcohol.getOrigin().toString());
        typeText.setText(alcohol.getType().getDisplayName());
        approvalDateText.setText(alcohol.getApprovalDate().toString());
        ttbID.setText("TTB ID #" + String.valueOf(alcohol.getId()));
        labelImage.setImage(alcohol.getLabelImage().display());
        if(alcohol.getFormula() == -1){
            formulaText.setText("N/A");
        } else {
            formulaText.setText(String.valueOf(alcohol.getFormula()));
        }
        ImageUtils.centerImage(labelImage);

        User user = Storage.getInstance().getUser(alcohol.getApplicantID());
        userNameText.setText(user.getName());
        emailText.setText(user.getEmail().getEmailAddress());
        phoneText.setText(user.getPhoneNumber().getFormattedNumber());
        companyText.setText(user.getCompany());
        permitText.setText(String.valueOf(user.getPermitNo()));
        addressText.setText(user.getAddress());

    }

    public void export(){

    }

    public void printPage(){
        PagePrinter.print(brandNameText.getScene());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JFXScrollPane.smoothScrolling(scrollPane);

        winePHHbox.managedProperty().bind(winePHHbox.visibleProperty());
        vintageYearHbox.managedProperty().bind(vintageYearHbox.visibleProperty());

        backBtn.setOnMouseClicked(mouseEvent -> {
            UIManager.Page page = UIManager.getInstance().loadPage(UIManager.SEARCH_PAGE);
            COLASearchController controller = page.getController();
            controller.setSearchTerm(searchTerm == null ? "" : searchTerm);
            UIManager.getInstance().displayPage(brandNameText.getScene(), page);
        });
    }
}
