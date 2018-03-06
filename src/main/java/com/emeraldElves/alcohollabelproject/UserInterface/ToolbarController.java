package com.emeraldElves.alcohollabelproject.UserInterface;

import com.emeraldElves.alcohollabelproject.Authenticator;
import com.emeraldElves.alcohollabelproject.Data.UserType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;
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
        onLoginPage = false;
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
//                main.loadFXML("/fxml/Login.fxml");
                break;
        }
    }

    public void utilityButton() {
//        switch (Authenticator.getInstance().getUserType()) {
//            case TTBAGENT:
//                main.loadFXML("/fxml/TTBWorkflowPage.fxml");
//                break;
//            case APPLICANT:
//                main.loadFXML("/fxml/ApplicantWorkflowPage.fxml");
//                break;
//            case SUPERAGENT:
//                main.loadFXML("/fxml/SuperagentWorkflowPage.fxml");
//                break;
//            case BASIC:
//                utility.setVisible(true);
//                main.loadFXML("/fxml/NewUser.fxml");
//                break;
//        }
    }

    public void extraFunction() {
//        if (Authenticator.getInstance().getUserType() == UserType.APPLICANT) {
//            main.loadFXML("/fxml/ProfilePage.fxml");
//        }
//        if (Authenticator.getInstance().getUserType() == UserType.SUPERAGENT){
//            main.loadFXML("/fxml/SuperagentViewAllApplications.fxml");
//        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        switch (Authenticator.getInstance().getUserType()) {
            case SUPERAGENT:
                extraButton.setVisible(true);
                extraButton.setText("REVIEW ALL APPLICATIONS");
                utility.setVisible(true);
                utility.setText("REVIEW POTENTIAL USERS");
                logButton.setText("LOG OUT");
                break;
            case TTBAGENT:
                extraButton.setVisible(false);
                utility.setVisible(true);
                utility.setText("APPLICATIONS");
                logButton.setText("LOG OUT");
                break;
            case APPLICANT:
                extraButton.setVisible(true);
                utility.setVisible(true);
                utility.setText("MY APPLICATIONS");
                logButton.setText("LOG OUT");
                break;
            default:
                extraButton.setVisible(false);
                utility.setVisible(true);
                utility.setText("APPLY FOR ACCOUNT");
                logButton.setText("LOGIN");
                break;
        }
        if (onLoginPage) {
            logButton.setVisible(false);
        }
    }
}
