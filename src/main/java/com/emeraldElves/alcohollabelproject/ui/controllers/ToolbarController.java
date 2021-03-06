package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Authenticator;
import com.emeraldElves.alcohollabelproject.Data.UserType;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.emeraldElves.alcohollabelproject.ui.controllers.ApplicationSubmissionController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kylec on 4/18/2017.
 */
public class ToolbarController implements Initializable {

    @FXML
    private Button utility;
    @FXML
    private Button logButton;
    @FXML
    private Button extraButton;


    public static boolean onLoginPage = false;

    public void goHome() {
        UIManager.getInstance().displayPage(logButton.getScene(), UIManager.HOME_PAGE);
    }

    public void loadLog() {
        switch (Authenticator.getInstance().getUserType()) {
            case TTBAGENT:
                Authenticator.getInstance().logout();
                goHome();
                break;
            case APPLICANT:
                Authenticator.getInstance().logout();
                goHome();
                break;
            case SUPERAGENT:
                Authenticator.getInstance().logout();
                goHome();
                break;
            case BASIC:
                UIManager.getInstance().displayPage(logButton.getScene(), UIManager.LOGIN_PAGE);
                break;
        }
    }

    public void utilityButton() {

        switch (Authenticator.getInstance().getUserType()) {
            case BASIC:
                utility.setVisible(true);
                UIManager.getInstance().displayPage(utility.getScene(), UIManager.CREATE_ACCOUNT_PAGE);
                break;
            case APPLICANT:
                UIManager.getInstance().displayPage(utility.getScene(), UIManager.NEW_APPLICATION_PAGE);
                break;
            case SUPERAGENT:
                UIManager.getInstance().displayPage(utility.getScene(), UIManager.APPROVE_USER_PAGE);
                break;
            case TTBAGENT:
                UIManager.getInstance().displayPage(utility.getScene(), UIManager.APPLICATION_APPROVAL_PAGE);
                break;
        }
    }

    public void extraFunction() {
        if (Authenticator.getInstance().getUserType() == UserType.APPLICANT) {
            UIManager.getInstance().displayPage(extraButton.getScene(), UIManager.MY_APPLICATIONS_PAGE);
        }
//        if (Authenticator.getInstance().getUserType() == UserType.SUPERAGENT){
//            main.loadFXML("/fxml/SuperagentViewAllApplications.fxml");
//        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        extraButton.managedProperty().bind(extraButton.visibleProperty());
        utility.managedProperty().bind(utility.visibleProperty());
        logButton.managedProperty().bind(logButton.visibleProperty());

        switch (Authenticator.getInstance().getUserType()) {
            case SUPERAGENT:
                extraButton.setVisible(true);
                extraButton.setText("Review all Applications");
                utility.setVisible(true);
                utility.setText("Review New Users");
                logButton.setText("Log Out");
                break;
            case TTBAGENT:
                extraButton.setVisible(false);
                utility.setVisible(true);
                utility.setText("Applications");
                logButton.setText("Log Out");
                break;
            case APPLICANT:
                extraButton.setVisible(true);
                extraButton.setText("My Applications");
                utility.setVisible(true);
                utility.setText("Create Application");
                logButton.setText("Log Out");
                break;
            default:
                extraButton.setVisible(false);
                utility.setVisible(true);
                utility.setText("Apply for Account");
                logButton.setText("Login");
                break;
        }
        logButton.setVisible(!onLoginPage);
    }
}
