package com.emeraldElves.alcohollabelproject;

import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableRow;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by keionbis on 4/4/17.
 */

public class ApplicantWorkflowController {
   String Username;
    Main main;
    private ListView <SubmittedApplication> ApplicationsList;
    public void init(String Username, Main main){
        this.Username = Username;;
        this.main = main;
    }
    AlcoholDatabase alcoholDatabase = new AlcoholDatabase((Main.database));
    AuthenticatedUsersDatabase RepID = new AuthenticatedUsersDatabase(Main.database);
    public void ApplicationWorkflow() {
        ApplicationsList = new ListView<SubmittedApplication>(FXCollections.observableArrayList(alcoholDatabase.getApplicationsByRepresentative(RepID.getRepresentativeID(Username))));
        ApplicationsList.getSelectionModel().getSelectedItem();


    }
}
