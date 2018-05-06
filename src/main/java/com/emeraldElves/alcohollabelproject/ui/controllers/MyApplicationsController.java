package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Authenticator;
import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.COLASubmissionHandler;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyApplicationsController implements Initializable {


    @FXML
    private COLADetailPane colaInfo;

    @FXML
    private ImageView labelImage;

    @FXML
    private JFXButton createBtn, editBtn, withdrawBtn;

    @FXML
    private VBox applicationList, alcInfoVbox;

    @FXML
    private HBox actionButtons;

    @FXML
    private ScrollPane scrollPane, scrollPaneApplications;

    @FXML
    private Label noApplication;

    @FXML
    private GridPane root;

    private COLASubmissionHandler colaSubmissionHandler;

    private List<COLA> assignedApplications;

    private User user;
    private COLA cola;


    public MyApplicationsController(){
        colaSubmissionHandler = new COLASubmissionHandler();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBtn.setOnMouseClicked(mouseEvent -> createNew());
        editBtn.setOnMouseClicked(mouseEvent -> edit());
        withdrawBtn.setOnMouseClicked(mouseEvent -> withdraw());

        applicationList.getChildren().clear();

        withdrawBtn.managedProperty().bind(withdrawBtn.visibleProperty());
        noApplication.managedProperty().bind(noApplication.visibleProperty());
        alcInfoVbox.managedProperty().bind(alcInfoVbox.visibleProperty());
        actionButtons.managedProperty().bind(actionButtons.visibleProperty());

        noApplicationSelected();

        JFXScrollPane.smoothScrolling(scrollPane);
        JFXScrollPane.smoothScrolling(scrollPaneApplications);

        user = Authenticator.getInstance().getUser();

        Platform.runLater(() -> {
            assignedApplications = colaSubmissionHandler.getSubmittedCOLAS(user);
            populateApplications();
        });
    }

    private void withdraw() {
        if(cola != null){
            cola.setStatus(ApplicationStatus.WITHDRAWN);
            colaSubmissionHandler.submitCOLA(cola);
            withdrawBtn.setVisible(false);
        }
    }


    private void setApplication(COLA cola){
        this.cola = cola;

        colaInfo.setCOLA(cola);

        noApplication.setVisible(false);

        alcInfoVbox.setVisible(true);
        actionButtons.setVisible(true);

        labelImage.setImage(cola.getLabelImage().display());

        if(cola.getStatus() == ApplicationStatus.WITHDRAWN){
            withdrawBtn.setVisible(false);
        } else {
            withdrawBtn.setVisible(true);
        }

    }

    private void populateApplications(){
        List<COLA> colas = new ArrayList<>(assignedApplications);

        applicationList.getChildren().clear();

        if(colas.isEmpty()){
            VBox userListItem = new VBox();
            Label emptyLabel = new Label();
            emptyLabel.setText("No applications");
            userListItem.getChildren().add(emptyLabel);
            userListItem.getStyleClass().add("list-item-empty");
            applicationList.getChildren().add(userListItem);
        }

        for(COLA c: colas){
            VBox applicationListItem = new VBox();
            Label nameLabel = new Label();
            nameLabel.setText(c.getBrandName());
            if(c.getStatus() == ApplicationStatus.APPROVED){
                nameLabel.getStyleClass().add("approved");
            } else if (c.getStatus() == ApplicationStatus.REJECTED){
                nameLabel.getStyleClass().add("rejected");
            }

            Label idLabel = new Label();
            idLabel.setText(String.format("TTB ID #%d", c.getId()));
            idLabel.getStyleClass().add("subhead");
            applicationListItem.getChildren().addAll(nameLabel, idLabel);
            applicationListItem.getStyleClass().add("list-item");

            applicationListItem.setOnMouseClicked(mouseEvent -> {
                applicationList.getChildren().forEach(node -> node.getStyleClass().remove("list-item-selected"));
                applicationListItem.getStyleClass().add("list-item-selected");
                setApplication(c);
            });

            applicationList.getChildren().add(applicationListItem);
        }

        if(colas.isEmpty()) {
            noApplicationSelected();
        } else {
            scrollPane.setVvalue(0);
            applicationList.getChildren().get(0).getStyleClass().add("list-item-selected");
            setApplication(colas.get(0));
        }
    }

    private void edit(){
        if(cola == null)
            return;
        // Do something
    }

    private void createNew(){
        UIManager.getInstance().displayPage(scrollPane.getScene(), UIManager.NEW_APPLICATION_PAGE);
    }

    public void exportAsPDF(){
        ExportApplicationsController exporter = new ExportApplicationsController(root);
        if(cola != null) {
            exporter.exportPDF(cola, user);
        } else {
            NotificationController notificationController = new NotificationController();
            notificationController.notify(root, "No COLA selected");
        }
    }

    private void noApplicationSelected(){
        this.cola = null;

        alcInfoVbox.setVisible(false);
        actionButtons.setVisible(false);

        noApplication.setVisible(true);
    }
}
