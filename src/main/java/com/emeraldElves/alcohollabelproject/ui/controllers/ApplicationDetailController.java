package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.Storage;
import com.emeraldElves.alcohollabelproject.ui.ImageUtils;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationDetailController implements Initializable {

    @FXML
    private COLADetailPane colaInfo;

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

    @FXML
    private GridPane root;

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
        colaInfo.setCOLA(alcohol);
        ttbID.setText("TTB ID #" + String.valueOf(alcohol.getId()));
        labelImage.setImage(alcohol.getLabelImage().display());
        ImageUtils.centerImage(labelImage);

        User user = Storage.getInstance().getUser(alcohol.getApplicantID());
        if(user != null) {
            userNameText.setText(user.getName());
            emailText.setText(user.getEmail().getEmailAddress());
            phoneText.setText(user.getPhoneNumber().getFormattedNumber());
            companyText.setText(user.getCompany());
            permitText.setText(user.getPermitNo());
            addressText.setText(user.getAddress());
        }

    }

    public void export(){

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JFXScrollPane.smoothScrolling(scrollPane);


        backBtn.setOnMouseClicked(mouseEvent -> {
            UIManager.Page page = UIManager.getInstance().loadPage(UIManager.SEARCH_PAGE);
            COLASearchController controller = page.getController();
            controller.setSearchTerm(searchTerm == null ? "" : searchTerm);
            UIManager.getInstance().displayPage(root.getScene(), page);
        });
    }
}
