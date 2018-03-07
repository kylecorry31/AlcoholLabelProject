package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Data.UserType;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.Storage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {

    @FXML
    private JFXTextField emailText;

    @FXML
    private JFXPasswordField passwordText;

    @FXML
    private JFXRadioButton radioAlcoholProducer, radioTTBAgent;

    @FXML
    private JFXButton createButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createButton.setOnMouseClicked(mouseEvent -> submit());
    }

    private void submit(){
        String email = emailText.getText();
        String password = passwordText.getText();
        UserType type = getUserType();

        User user = new User(email, password, type);

        Storage.getInstance().saveUser(user);

        UIManager.getInstance().displayPage(emailText.getScene(), UIManager.HOME_PAGE);
    }

    private UserType getUserType(){
        if(radioAlcoholProducer.isSelected())
            return UserType.APPLICANT;
        return UserType.TTBAGENT;
    }

}
