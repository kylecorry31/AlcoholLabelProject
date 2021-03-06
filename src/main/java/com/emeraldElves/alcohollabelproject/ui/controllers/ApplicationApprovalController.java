package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Authenticator;
import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.COLAApprovalHandler;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.Storage;
import com.emeraldElves.alcohollabelproject.ui.modules.COLADetailPane;
import com.emeraldElves.alcohollabelproject.ui.modules.UserDetailPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ApplicationApprovalController implements Initializable {

    @FXML
    private UserDetailPane userInfo;

    @FXML
    private ImageView labelImage;

    @FXML
    private JFXButton approveBtn, rejectBtn, fetchBtn;

    @FXML
    private VBox applicationList, alcInfoVbox;

    @FXML
    private HBox actionButtons;

    @FXML
    private ScrollPane scrollPane, scrollPaneApplications;

    @FXML
    private Label noApplication;

    @FXML
    private JFXDatePicker expirationDate;


    private COLAApprovalHandler colaApprovalHandler;

    private List<COLA> assignedApplications;

    private User user;
    private COLA cola;

    @FXML
    private COLADetailPane colaInfo;


    public ApplicationApprovalController(){
        colaApprovalHandler = new COLAApprovalHandler();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        approveBtn.setOnMouseClicked(mouseEvent -> approve());
        rejectBtn.setOnMouseClicked(mouseEvent -> reject());
        fetchBtn.setOnMouseClicked(mouseEvent -> fetchMoreApplications());

        applicationList.getChildren().clear();

        noApplication.managedProperty().bind(noApplication.visibleProperty());
        alcInfoVbox.managedProperty().bind(alcInfoVbox.visibleProperty());
        actionButtons.managedProperty().bind(actionButtons.visibleProperty());

        noApplicationSelected();

        JFXScrollPane.smoothScrolling(scrollPane);
        JFXScrollPane.smoothScrolling(scrollPaneApplications);

        user = Authenticator.getInstance().getUser();

        Platform.runLater(() -> {
            assignedApplications = colaApprovalHandler.getAssignedApplications(user);
            if (assignedApplications.size() < 10){
                fetchMoreApplications();
            } else {
                populateApplications();
            }
        });
    }


    private void setApplication(COLA cola){
        this.cola = cola;

        colaInfo.setCOLA(cola);

        noApplication.setVisible(false);

        alcInfoVbox.setVisible(true);
        actionButtons.setVisible(true);

        labelImage.setImage(cola.getLabelImage().display());

        User submitter = Storage.getInstance().getUser(cola.getApplicantID());

        if(submitter != null){
            userInfo.setUser(submitter);
        }

    }

    private void fetchMoreApplications(){
        assignedApplications = colaApprovalHandler.assignCOLAs(user, 10 - assignedApplications.size()); // TODO: make count a setting
        populateApplications();
    }

    private void populateApplications(){
        List<COLA> colas = new ArrayList<>(assignedApplications);
        colas.removeIf(cola1 -> cola1.getStatus() != ApplicationStatus.RECEIVED);

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

    private void approve(){
        if (cola == null)
            return;

        if (expirationDate.getValue() != null){
            cola.setExpirationDate(expirationDate.getValue());
        }

        expirationDate.setValue(null);

        colaApprovalHandler.approveCOLA(cola, "Your application has been approved");
        assignedApplications.remove(cola);
        Platform.runLater(this::fetchMoreApplications);
    }

    private void reject(){
        if(cola == null)
            return;

        expirationDate.setValue(null);

        colaApprovalHandler.rejectCOLA(cola, "Your application has been rejected");
        assignedApplications.remove(cola);
        Platform.runLater(this::fetchMoreApplications);
    }

    private void noApplicationSelected(){
        this.cola = null;

        alcInfoVbox.setVisible(false);
        actionButtons.setVisible(false);

        noApplication.setVisible(true);
    }
}
