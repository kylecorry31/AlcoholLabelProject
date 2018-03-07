package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.Storage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ApproveUsersController implements Initializable {

    @FXML
    private TableView<User> resultsTable;
    @FXML
    private TableColumn<User, String> repCol;
    @FXML
    private TableColumn<User, String> nameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> userTypeCol;
    @FXML
    private TableColumn<User, String> idNumberCol;

    private ObservableList<User> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<User> users = Storage.getInstance().getAllUsers();
        users.removeIf(User::isApproved);

        data.addAll(users);

        nameCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getName()));
        emailCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getEmail().getEmailAddress()));
        userTypeCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getType().toString()));
        idNumberCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(String.valueOf(p.getValue().getId())));
        repCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(String.format("%d / %d", p.getValue().getRepID(), p.getValue().getPermitNo())));

        resultsTable.setItems(data);
        resultsTable.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    User rowData = row.getItem();
                    // Detail view for user
                }
            });
            return row;
        });

    }


    public void approveUser(){
        User user = resultsTable.getSelectionModel().getSelectedItem();
        if(user != null){
            user.setApproved(true);
            // Send email
            Storage.getInstance().updateUser(user);
            System.out.println("Approved user");
            data.remove(user);
        }
    }

    public void rejectUser(){
        User user = resultsTable.getSelectionModel().getSelectedItem();
        if(user != null){
            // Send email
            Storage.getInstance().deleteUser(user);
            data.remove(user);
        }
    }

}
