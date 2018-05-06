package com.emeraldElves.alcohollabelproject.ui.modules;

import com.emeraldElves.alcohollabelproject.data.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserDetailPane extends VBox {

    @FXML
    private Label userNameText, emailText, phoneText, companyText, permitText, addressText, repText;

    private User user;

    public UserDetailPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserDetailPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void setUser(User user ){
        this.user = user;
        updateUI();
    }

    private void updateUI(){
        if(user == null)
            return;

        userNameText.setText(user.getName());
        emailText.setText(user.getEmail().getEmailAddress());
        phoneText.setText(user.getPhoneNumber().getFormattedNumber());
        companyText.setText(user.getCompany());
        permitText.setText(user.getPermitNo());
        addressText.setText(user.getAddress());
        repText.setText(String.valueOf(user.getRepID()));
    }

}
