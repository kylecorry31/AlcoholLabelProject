package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Authenticator;
import com.emeraldElves.alcohollabelproject.Data.PasswordStrengthChecker;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Essam on 4/4/2017.
 */
public class LoginController implements Initializable{
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    VBox errorMsg;
    private StrongPasswordEncryptor EncryptPassword = new StrongPasswordEncryptor();
    private PasswordStrengthChecker strengthChecker = new PasswordStrengthChecker();

    public void login(ActionEvent e) throws UnsupportedEncodingException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (password == null || password.isEmpty()){
            errorMsg.setVisible(true);
            return;
        }
        boolean login = Authenticator.getInstance().login(username, password);

        if (login) {
            errorMsg.setVisible(false);
            UIManager.getInstance().displayPage(usernameField.getScene(), UIManager.HOME_PAGE);
        } else {
            errorMsg.setVisible(true);
        }
//        if(!strengthChecker.isPasswordValid(password)&&!Authenticator.getInstance().isSuperAgentLoggedIn()){
//            main.loadFXML("/fxml/UpdatePassword.fxml");
//        }
    }
    public void loadForgotPasswordController(){
//        main.loadFXML("/fxml/Forgotpassword.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
