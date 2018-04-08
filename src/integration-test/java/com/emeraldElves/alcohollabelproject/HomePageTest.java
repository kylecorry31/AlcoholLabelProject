package com.emeraldElves.alcohollabelproject;

import com.emeraldElves.alcohollabelproject.database.ApacheDerbyDatabase;
import com.emeraldElves.alcohollabelproject.database.IDatabase;
import com.emeraldElves.alcohollabelproject.database.Storage;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class HomePageTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        IDatabase database = new ApacheDerbyDatabase("colaTest.db");
        Storage.getInstance().setDatabase(database);
        primaryStage.setTitle("Alcohol Label Project");
        primaryStage.getIcons().add(new Image(("images/logo.png")));
        UIManager.Page page = UIManager.getInstance().loadPage(UIManager.HOME_PAGE);
        Parent root = page.getRoot();
        root.getStylesheets().add("/style/style.css");
        primaryStage.setScene(new Scene(root,1024,768));
        primaryStage.show();
    }

    @Test
    public void test() throws InterruptedException {
        clickOn("#about");
        verifyThat("#credits", hasText("Credits"));
    }
}
