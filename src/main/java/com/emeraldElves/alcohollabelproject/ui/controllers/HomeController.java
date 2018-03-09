package com.emeraldElves.alcohollabelproject.ui.controllers;


import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.data.COLASearchHandler;
import com.emeraldElves.alcohollabelproject.data.search.StatusFilter;
import com.emeraldElves.alcohollabelproject.ui.*;
import com.emeraldElves.alcohollabelproject.data.COLA;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.*;

/**
 * Created by Harry and Joe on 4/2/2017.
 */
public class HomeController implements Initializable {

    // Alcohol 1
    @FXML
    private AnchorPane alc1;
    @FXML
    private Label brand1;
    @FXML
    private Label fanciful1;
    @FXML
    private Label content1;
    @FXML
    private Label date1;
    @FXML
    private ImageView image1;

    // Alcohol 2
    @FXML
    private AnchorPane alc2;
    @FXML
    private Label brand2;
    @FXML
    private Label fanciful2;
    @FXML
    private Label content2;
    @FXML
    private Label date2;
    @FXML
    private ImageView image2;

    // Alcohol 3
    @FXML
    private AnchorPane alc3;
    @FXML
    private Label brand3;
    @FXML
    private Label fanciful3;
    @FXML
    private Label content3;
    @FXML
    private Label date3;
    @FXML
    private ImageView image3;

    // Alcohol 4
    @FXML
    private AnchorPane alc4;
    @FXML
    private Label brand4;
    @FXML
    private Label fanciful4;
    @FXML
    private Label content4;
    @FXML
    private Label date4;
    @FXML
    private ImageView image4;

    @FXML
    private TextField searchbox;

    private List<COLA> alcohol;

    private COLASearchHandler colaSearchHandler;

    public HomeController(){
        colaSearchHandler = new COLASearchHandler();
    }

    public void aboutProject(){
        UIManager.getInstance().displayPage(searchbox.getScene(), UIManager.ABOUT_PAGE);
    }


    public void searchDatabase() {
        UIManager.Page page = UIManager.getInstance().loadPage(UIManager.SEARCH_PAGE);
        COLASearchController controller = page.getController();
        controller.setSearchTerm(searchbox.getText());
        UIManager.getInstance().displayPage(searchbox.getScene(), page);
    }

    public void feelingThirsty() {
        Random random = new Random();
        COLA app;
        if (alcohol.isEmpty()) {
            app = null;
        } else {
            int pos = random.nextInt(alcohol.size());
            app = alcohol.get(pos);
        }

        UIManager.Page page = UIManager.getInstance().loadPage(UIManager.APPLICATION_DETAIL_PAGE);
        ApplicationDetailController controller = page.getController();
        controller.setAlcohol(app);
        UIManager.getInstance().displayPage(searchbox.getScene(), page);
    }


    private void populateRecentList(){
        for (int i = 0; i < alcohol.size(); i++) {
            COLA recentApplication = alcohol.get(i);
            switch (i) {
                case 0:
                    populateRecentItem(alc1, brand1, fanciful1, content1, date1, image1, recentApplication);
                    break;
                case 1:
                    populateRecentItem(alc2, brand2, fanciful2, content2, date2, image2, recentApplication);
                    break;
                case 2:
                    populateRecentItem(alc3, brand3, fanciful3, content3, date3, image3, recentApplication);
                    break;
                case 3:
                    populateRecentItem(alc4, brand4, fanciful4, content4, date4, image4, recentApplication);
                    break;
            }
        }
    }


    private void populateRecentItem(AnchorPane item, Label brandName, Label fancifulName, Label alcoholContent, Label date, ImageView image, COLA alcohol){
        item.setOnMouseClicked(event -> {
            UIManager.Page page = UIManager.getInstance().loadPage(UIManager.APPLICATION_DETAIL_PAGE);
            ApplicationDetailController controller = page.getController();
            controller.setAlcohol(alcohol);
            UIManager.getInstance().displayPage(item.getScene(), page);
        });
        brandName.setText(alcohol.getBrandName().toUpperCase());
        fancifulName.setText(alcohol.getFancifulName());
        alcoholContent.setText(String.format("%.1f%%", alcohol.getAlcoholContent()));
        date.setText(alcohol.getApprovalDate().toString());
        image.setImage(alcohol.getLabelImage().display());
        ImageUtils.centerImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            colaSearchHandler.receiveAllCOLAs();
            alcohol = colaSearchHandler.filteredSearch(new StatusFilter(ApplicationStatus.APPROVED));

            Platform.runLater(() -> {
                populateRecentList();
                AutoCompletionBinding<String> autoCompletionBinding;
                Set<String> possibleSuggestions = new HashSet<>();
                possibleSuggestions.clear();

                for (COLA cola : alcohol) {
                    possibleSuggestions.add(cola.getBrandName());
                    possibleSuggestions.add(cola.getFancifulName());
                }

                autoCompletionBinding = TextFields.bindAutoCompletion(searchbox, possibleSuggestions);
                autoCompletionBinding.setPrefWidth(searchbox.getPrefWidth());

                searchbox.setOnKeyPressed(ke -> autoCompletionBinding.setUserInput(searchbox.getText().trim()));
            });
        }).start();
    }
}
