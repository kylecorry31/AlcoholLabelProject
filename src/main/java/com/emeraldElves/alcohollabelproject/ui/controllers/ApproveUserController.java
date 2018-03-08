package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.LogManager;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.Storage;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ApproveUserController implements Initializable {

    @FXML
    private Label nameText, privilegeText, phoneText, emailText,
                    repText, permitText, companyText, addressText,
                    noUser;

    @FXML
    private Label name, privilege, phone, email,
            repID, permit, company, address;

    @FXML
    private JFXButton approveBtn, rejectBtn;

    @FXML
    private VBox userList;

    private User user;

    @FXML
    private ScrollPane scrollPane, scrollPaneUsers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        approveBtn.setOnMouseClicked(mouseEvent -> approve());
        rejectBtn.setOnMouseClicked(mouseEvent -> reject());

        noUser.managedProperty().bind(noUser.visibleProperty());

        noUserSelected();

        JFXScrollPane.smoothScrolling(scrollPane);
        JFXScrollPane.smoothScrolling(scrollPaneUsers);

        Platform.runLater(this::populateUsers);
    }

    private void populateUsers(){
        List<User> users = Storage.getInstance().getAllUsers();
        users.removeIf(User::isApproved);

        userList.getChildren().clear();

        if(users.isEmpty()){
            VBox userListItem = new VBox();
            Label emptyLabel = new Label();
            emptyLabel.setText("No users");
            userListItem.getChildren().add(emptyLabel);
            userListItem.getStyleClass().add("list-item-empty");
            userList.getChildren().add(userListItem);
        }

        for(User user: users){
            VBox userListItem = new VBox();
            Label nameLabel = new Label();
            nameLabel.setText(user.getName());
            Label privilegeLabel = new Label();
            privilegeLabel.setText(user.getType().getDisplayName());
            privilegeLabel.getStyleClass().add("subhead");
            userListItem.getChildren().addAll(nameLabel, privilegeLabel);
            userListItem.getStyleClass().add("list-item");

            userListItem.setOnMouseClicked(mouseEvent -> {
                userList.getChildren().forEach(node -> node.getStyleClass().remove("list-item-selected"));
                userListItem.getStyleClass().add("list-item-selected");
                setUser(user);
            });

            userList.getChildren().add(userListItem);
        }

        noUserSelected();
    }


    public void setUser(User user){
        this.user = user;

        nameText.setVisible(true);
        privilegeText.setVisible(true);
        phoneText.setVisible(true);
        emailText.setVisible(true);
        repText.setVisible(true);
        permitText.setVisible(true);
        companyText.setVisible(true);
        addressText.setVisible(true);
        approveBtn.setVisible(true);
        rejectBtn.setVisible(true);
        noUser.setVisible(false);

        name.setVisible(true);
        privilege.setVisible(true);
        phone.setVisible(true);
        email.setVisible(true);
        repID.setVisible(true);
        permit.setVisible(true);
        company.setVisible(true);
        address.setVisible(true);

        nameText.setText(user.getName());
        privilegeText.setText(user.getType().getDisplayName());
        phoneText.setText(user.getPhoneNumber().getFormattedNumber());
        emailText.setText(user.getEmail().getEmailAddress());
        repText.setText(String.valueOf(user.getRepID()));
        permitText.setText(String.valueOf(user.getPermitNo()));
        companyText.setText(user.getCompany());
        addressText.setText(user.getAddress());
    }

    private void noUserSelected(){
        this.user = null;
        nameText.setVisible(false);
        privilegeText.setVisible(false);
        phoneText.setVisible(false);
        emailText.setVisible(false);
        repText.setVisible(false);
        permitText.setVisible(false);
        companyText.setVisible(false);
        addressText.setVisible(false);

        name.setVisible(false);
        privilege.setVisible(false);
        phone.setVisible(false);
        email.setVisible(false);
        repID.setVisible(false);
        permit.setVisible(false);
        company.setVisible(false);
        address.setVisible(false);

        approveBtn.setVisible(false);
        rejectBtn.setVisible(false);
        noUser.setVisible(true);
    }


    private void approve(){
        if(user == null)
            return;

        user.setApproved(true);
        Storage.getInstance().updateUser(user);
        LogManager.getInstance().log(ApproveUserController.class.getSimpleName(), "Approved user: " + user.getId());
//        UIManager.getInstance().displayPage(nameText.getScene(), UIManager.APPROVE_USERS_PAGE);
        populateUsers();
    }

    private void reject(){
        Storage.getInstance().deleteUser(user);
        LogManager.getInstance().log(ApproveUserController.class.getSimpleName(), "Rejected user: " + user.getId());
//        UIManager.getInstance().displayPage(nameText.getScene(), UIManager.APPROVE_USERS_PAGE);
        populateUsers();
    }



}
