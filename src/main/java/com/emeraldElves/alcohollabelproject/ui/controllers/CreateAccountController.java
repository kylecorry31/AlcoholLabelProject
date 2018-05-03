package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Data.PhoneNumber;
import com.emeraldElves.alcohollabelproject.Data.UserType;
import com.emeraldElves.alcohollabelproject.LogManager;
import com.emeraldElves.alcohollabelproject.PasswordStrengthChecker;
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
    private JFXPasswordField passwordText, confirmPasswordText;

    @FXML
    private JFXRadioButton radioAlcoholProducer, radioTTBAgent;

    @FXML
    private JFXButton createButton;

    @FXML
    private JFXProgressBar passwordStrength;

    private List<ValidatorBase> validators;

    private PasswordStrengthChecker strengthChecker;


    public CreateAccountController() {
        validators = new LinkedList<>();
        strengthChecker = new PasswordStrengthChecker();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createButton.setOnMouseClicked(mouseEvent -> submit());

        permitText.managedProperty().bind(permitText.visibleProperty());
        companyText.managedProperty().bind(companyText.visibleProperty());
        addressText.managedProperty().bind(addressText.visibleProperty());

        radioTTBAgent.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1){
                permitText.setVisible(false);
                companyText.setVisible(false);
                addressText.setVisible(false);
                permitText.clear();
                companyText.clear();
                addressText.clear();
            } else {
                permitText.setVisible(true);
                companyText.setVisible(true);
                addressText.setVisible(true);
            }
        });

        passwordText.textProperty().addListener(observable -> {
            if (passwordText.getText().isEmpty()){
                passwordStrength.setProgress(0);
            } else {
                PasswordStrengthChecker.Strength strength = strengthChecker.check(passwordText.getText());
                String[] strengthStyles = {"password-strength-weak", "password-strength-fair", "password-strength-good", "password-strength-strong", "password-strength-very-strong"};
                switch (strength) {
                    case WEAK:
                        passwordStrength.setProgress(0.2);
                        passwordStrength.getStyleClass().removeAll(strengthStyles);
                        passwordStrength.getStyleClass().add(strengthStyles[0]);
                        break;
                    case FAIR:
                        passwordStrength.setProgress(0.4);
                        passwordStrength.getStyleClass().removeAll(strengthStyles);
                        passwordStrength.getStyleClass().add(strengthStyles[1]);
                        break;
                    case GOOD:
                        passwordStrength.setProgress(0.6);
                        passwordStrength.getStyleClass().removeAll(strengthStyles);
                        passwordStrength.getStyleClass().add(strengthStyles[2]);
                        break;
                    case STRONG:
                        passwordStrength.setProgress(0.8);
                        passwordStrength.getStyleClass().removeAll(strengthStyles);
                        passwordStrength.getStyleClass().add(strengthStyles[3]);
                        break;
                    case VERY_STRONG:
                        passwordStrength.setProgress(1);
                        passwordStrength.getStyleClass().removeAll(strengthStyles);
                        passwordStrength.getStyleClass().add(strengthStyles[4]);
                        break;
                }
            }
        });

        addValidator(emailText, new EmailAddressValidator(), "Invalid email address");
        addValidator(phoneText, new PhoneNumberValidator(), "Invalid phone number");
        addValidator(nameText, new RequiredFieldValidator(), "Name required");
        addValidator(passwordText, new RequiredFieldValidator(), "Password required");
        addValidator(repText, new OptionalNumberValidator(), "Representative ID must be a number");
        addValidator(emailText, new UniqueUserValidator(), "Email address taken");
        addValidator(confirmPasswordText, new TextEqualValidator(passwordText), "Passwords are different");

    }

    private void submit(){

        emailText.validate();
        phoneText.validate();
        nameText.validate();
        passwordText.validate();
        repText.validate();

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

        String permitNo = permitText.getText();

        UserType type = getUserType();

        User user = new User(email, name, password, type);
        user.setPhoneNumber(new PhoneNumber(phone));
        user.setAddress(address);
        user.setCompany(company);
        user.setRepID(repId);
        user.setPermitNo(permitNo);

        Storage.getInstance().saveUser(user);

        LogManager.getInstance().log(getClass().getSimpleName(), "New user " + user.getEmail().getEmailAddress());


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
