package com.emeraldElves.alcohollabelproject;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.Data.ProductSource;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.database.ApacheDerbyDatabase;
import com.emeraldElves.alcohollabelproject.database.IDatabase;
import com.emeraldElves.alcohollabelproject.database.Storage;
import com.emeraldElves.alcohollabelproject.ui.Main;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;

public class HomePageTest extends ApplicationTest {

    private IDatabase database;

    private long screenLoadWaitTime = 500;


    @Override
    public void start(Stage primaryStage) throws Exception {
        database = new ApacheDerbyDatabase("colaTest.db");
        database.connect();
        database.dropTable(COLA.DB_TABLE);
        database.dropTable(User.DB_TABLE);
        Storage.getInstance().setDatabase(database);
        COLA c1 = new COLA(1, "Test'Brand", AlcoholType.BEER, "180001", ProductSource.DOMESTIC);
        c1.setAlcoholContent(10);
        c1.setFancifulName("TestFanciful");
        c1.setStatus(ApplicationStatus.APPROVED);
        Storage.getInstance().saveCOLA(c1);
        primaryStage.setTitle("Alcohol Label Project");
        primaryStage.getIcons().add(new Image(("images/logo.png")));
        UIManager.Page page = UIManager.getInstance().loadPage(UIManager.HOME_PAGE);
        Parent root = page.getRoot();
        root.getStylesheets().add("/style/style.css");
        primaryStage.setScene(new Scene(root,1024,768));
        primaryStage.show();
        primaryStage.toFront();
    }


    @After
    public void after() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        database.disconnect();
    }

    @Test
    public void testRandom() throws InterruptedException {
        clickOn("#randomBtn");
        Thread.sleep(screenLoadWaitTime);
        verifyThat("#brandNameText", LabeledMatchers.hasText("Test'Brand"));
    }

    @Test
    public void testSearch() throws Exception {
        clickOn("#searchbox").write("test");

        clickOn("#searchBtn");
        Thread.sleep(screenLoadWaitTime);
        verifyThat("#searchPage", Node::isVisible);

        verifyThat("#searchField", TextInputControlMatchers.hasText("test"));
    }

    @Test
    public void testAbout() throws Exception {
        clickOn("#about");
        Thread.sleep(screenLoadWaitTime);
        verifyThat("#credits", Node::isVisible);
    }
}
