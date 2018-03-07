package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.LogManager;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.Storage;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ApproveUserController implements Initializable {

    @FXML
    private Label nameText, privilegeText, phoneText, emailText,
                    repText, permitText, companyText, addressText,
                    dbID;

    @FXML
    private JFXButton approveBtn, rejectBtn;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        approveBtn.setOnMouseClicked(mouseEvent -> approve());
        rejectBtn.setOnMouseClicked(mouseEvent -> reject());
    }


    public void setUser(User user){
        this.user = user;

        nameText.setText(user.getName());
        privilegeText.setText(user.getType().getDisplayName());
        phoneText.setText(user.getPhoneNumber().getFormattedNumber());
        emailText.setText(user.getEmail().getEmailAddress());
        repText.setText(String.valueOf(user.getRepID()));
        permitText.setText(String.valueOf(user.getPermitNo()));
        companyText.setText(user.getCompany());
        addressText.setText(user.getAddress());
        dbID.setText(String.format("ID #%d", user.getId()));
    }


    private void approve(){
        if(user == null)
            return;

        user.setApproved(true);
        Storage.getInstance().updateUser(user);
        LogManager.getInstance().log(ApproveUserController.class.getName(), "Approved user: " + user.getId());
        UIManager.getInstance().displayPage(nameText.getScene(), UIManager.APPROVE_USERS_PAGE);
    }

    private void reject(){
        Storage.getInstance().deleteUser(user);
        LogManager.getInstance().log(ApproveUserController.class.getName(), "Rejected user: " + user.getId());
        UIManager.getInstance().displayPage(nameText.getScene(), UIManager.APPROVE_USERS_PAGE);
    }



}
