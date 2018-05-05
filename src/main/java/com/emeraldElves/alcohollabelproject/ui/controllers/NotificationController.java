package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.jfoenix.controls.JFXSnackbar;
import javafx.scene.layout.Pane;

public class NotificationController {

    public void notify(Pane pane, String text){
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.show(text, 3000);
    }

    public void notify(Pane pane, String text, String actionText, Runnable action){
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.show(text, actionText, actionEvent -> {
            action.run();
            snackbar.unregisterSnackbarContainer(pane);
        });
    }

}
