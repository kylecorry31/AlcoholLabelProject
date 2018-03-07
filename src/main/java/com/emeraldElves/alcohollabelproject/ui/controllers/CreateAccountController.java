package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Data.EmailAddress;
import com.emeraldElves.alcohollabelproject.Data.PhoneNumber;
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
    private JFXTextField emailText, nameText, companyText, addressText, phoneText, repText, permitText;

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
        String name = nameText.getText();
        String phone = phoneText.getText();
        String address = addressText.getText();
        String company = companyText.getText();
        long repId = -1;
        try {
            repId = Long.valueOf(repText.getText());
        } catch (Exception e){
            // Empty body
        }
        long permitNo = -1;
        try {
            permitNo = Long.valueOf(permitText.getText());
        } catch (Exception e){
            // Empty body
        }
        UserType type = getUserType();

        User user = new User(name, password, type);
        user.setEmail(new EmailAddress(email));
        user.setPhoneNumber(new PhoneNumber(phone));
        user.setAddress(address);
        user.setCompany(company);
        user.setRepID(repId);
        user.setPermitNo(permitNo);

        Storage.getInstance().saveUser(user);

        UIManager.getInstance().displayPage(emailText.getScene(), UIManager.HOME_PAGE);
    }

    private UserType getUserType(){
        if(radioAlcoholProducer.isSelected())
            return UserType.APPLICANT;
        return UserType.TTBAGENT;
    }

}
