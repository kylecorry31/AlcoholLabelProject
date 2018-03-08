package com.emeraldElves.alcohollabelproject.ui;

import com.emeraldElves.alcohollabelproject.ui.controllers.ToolbarController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIManager {

    public static final String HOME_PAGE = "/fxml/HomePage.fxml";
    public static final String ABOUT_PAGE = "/fxml/AboutPage.fxml";
    public static final String NEW_APPLICATION_PAGE = "/fxml/ApplicationSubmissionPage.fxml";
    public static final String APPLICATION_DETAIL_PAGE = "/fxml/ApplicationDetailPage.fxml";
    public static final String SEARCH_PAGE = "/fxml/Search.fxml";
    public static final String LOGIN_PAGE = "/fxml/Login.fxml";
    public static final String CREATE_ACCOUNT_PAGE = "/fxml/CreateAccountPage.fxml";
    public static final String APPROVE_USER_PAGE = "/fxml/ApproveUserPage.fxml";
    public static final String APPLICATION_APPROVAL_PAGE = "/fxml/ApplicationApprovalPage.fxml";
    public static final String MY_APPLICATIONS_PAGE = "/fxml/MyApplicationsPage.fxml";


    private static UIManager instance;

    private UIManager(){

    }

    public synchronized static UIManager getInstance(){
        if(instance == null)
            instance = new UIManager();
        return instance;
    }


    public Page loadPage(String page){
        ToolbarController.onLoginPage = page.equals(LOGIN_PAGE);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
        try {
            Parent root = loader.load();
            return new Page(loader, root, page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadStylesheets(Scene scene){
        scene.getStylesheets().add("/style/style.css");
    }

    public void displayPage(Scene scene, Page page){
        ToolbarController.onLoginPage = page.getUrl().equals(LOGIN_PAGE);
        loadStylesheets(scene);
        scene.setRoot(page.getRoot());
    }

    public void displayPage(Scene scene, String page){
        Page p = loadPage(page);
        displayPage(scene, p);
    }

    public class Page {
        private FXMLLoader loader;
        private Parent root;
        private String url;

        public Page(FXMLLoader loader, Parent root, String url) {
            this.loader = loader;
            this.root = root;
            this.url = url;
        }

        public Parent getRoot() {
            return root;
        }

        public <T> T getController(){
            return loader.getController();
        }

        public String getUrl() {
            return url;
        }
    }

}
