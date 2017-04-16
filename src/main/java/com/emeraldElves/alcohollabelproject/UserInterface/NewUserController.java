package com.emeraldElves.alcohollabelproject.UserInterface;

import com.emeraldElves.alcohollabelproject.Data.Storage;
import com.emeraldElves.alcohollabelproject.Data.UserType;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by Essam on 4/4/2017.
 */
public class NewUserController {
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    VBox errorMsg;

    private Main main;

    public NewUserController() {

    }

    public void init(Main main) {
        this.main = main;
    }

    public void createTTBAgent(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(Storage.getInstance().createUser(UserType.TTBAGENT, username, password)){
            errorMsg.setVisible(false);
            main.loadHomepage();
        } else {
            errorMsg.setVisible(true);
        }
    }

    public void createApplicant(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (Storage.getInstance().createUser(UserType.APPLICANT, username, password)) {
            errorMsg.setVisible(false);
            main.loadHomepage();
        } else {
            errorMsg.setVisible(true);
        }
    }

    public void GoHome(){
        main.loadHomepage();
    }
}
