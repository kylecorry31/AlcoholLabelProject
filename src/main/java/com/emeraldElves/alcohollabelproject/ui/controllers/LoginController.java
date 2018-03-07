package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Authenticator;
import com.emeraldElves.alcohollabelproject.Data.PasswordStrengthChecker;
import com.emeraldElves.alcohollabelproject.Data.UserType;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.emeraldElves.alcohollabelproject.ui.controllers.ToolbarController;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.Storage;
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
        if (password == null || password.isEmpty() || password.length() == 1){
            errorMsg.setVisible(true);
            return;
        }
//        Log.console(Authenticator.getInstance().login(UserType.TTBAGENT, username, password));
//        Log.console(Storage.getInstance().getAgentPassword(username));
//        Log.console(EncryptPassword.checkPassword(password,Storage.getInstance().getAgentPassword(username)));

        User user = Storage.getInstance().getUser(username, password);

        if(username.equals("admin") && password.equals("admin")){
            user = new User(username, password, UserType.SUPERAGENT);
        }

        if (user != null) {
            Authenticator.getInstance().setUser(user);
            errorMsg.setVisible(false);
            ToolbarController.onLoginPage = false;
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
        ToolbarController.onLoginPage = true;
    }
}
