package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Data.PhoneNumber;
import com.emeraldElves.alcohollabelproject.Data.UserType;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.Storage;
import com.emeraldElves.alcohollabelproject.ui.validation.*;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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

    private List<ValidatorBase> validators;


    public CreateAccountController() {
        validators = new LinkedList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createButton.setOnMouseClicked(mouseEvent -> submit());

        addValidator(emailText, new EmailAddressValidator(), "Invalid email address");
        addValidator(phoneText, new PhoneNumberValidator(), "Invalid phone number");
        addValidator(nameText, new RequiredFieldValidator(), "Name required");
        addValidator(passwordText, new RequiredFieldValidator(), "Password required");
        addValidator(repText, new OptionalNumberValidator(), "Representative ID must be a number");
        addValidator(permitText, new OptionalNumberValidator(), "Permit No. must be a number");
        addValidator(emailText, new UniqueUserValidator(), "Email address taken");

    }

    private void submit(){

        emailText.validate();
        phoneText.validate();
        nameText.validate();
        passwordText.validate();
        repText.validate();
        permitText.validate();

        boolean errors = false;
        for (ValidatorBase validator: validators){
            errors = errors || validator.getHasErrors();
        }

        if(errors){
            return;
        }

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

        User user = new User(email, name, password, type);
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

    private <T extends TextField & IFXTextInputControl> void addValidator(T textField, ValidatorBase validator, String message){
        validator.setMessage(message);

        textField.getValidators().add(validator);

        textField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                textField.validate();
            }
        });

        validators.add(validator);
    }

}
