package com.emeraldElves.alcohollabelproject.ui;

import com.emeraldElves.alcohollabelproject.database.ApacheDerbyDatabase;
import com.emeraldElves.alcohollabelproject.database.IDatabase;
import com.emeraldElves.alcohollabelproject.database.Storage;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private static IDatabase database;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Alcohol Label Project");
        primaryStage.getIcons().add(new Image(("images/logo.png")));
        UIManager.Page page = UIManager.getInstance().loadPage(UIManager.HOME_PAGE);
        Parent root = page.getRoot();
        root.getStylesheets().add("/style/style.css");
        primaryStage.setScene(new Scene(root,1024,768));
        primaryStage.show();

        primaryStage.setOnCloseRequest(windowEvent -> {
            if(database != null)
                database.disconnect();
            System.out.println("Shut down database");
        });
    }

    public static void main(String[] args) {
        database = new ApacheDerbyDatabase("cola.db");
        Storage.getInstance().setDatabase(database);

        launch(args);
    }


}
