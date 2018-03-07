package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.jfoenix.controls.JFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dan on 4/22/2017.
 */
public class AboutPageController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JFXScrollPane.smoothScrolling(scrollPane);
    }
}
