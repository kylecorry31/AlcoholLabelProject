package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.COLASearchHandler;
import com.emeraldElves.alcohollabelproject.data.search.*;
import com.emeraldElves.alcohollabelproject.database.CSVWriter;
import com.emeraldElves.alcohollabelproject.exporter.ApplicationExporter;
import com.emeraldElves.alcohollabelproject.ui.DialogFileSelector;
import com.emeraldElves.alcohollabelproject.ui.ImageUtils;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXScrollPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Essam on 4/2/2017.
 */
public class COLASearchController implements Initializable {

    private String searchTerm;

    @FXML
    private TextField searchField;

    @FXML
    private VBox searchList;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button saveBtn;

    @FXML
    private Label descriptionLabel;

    @FXML
    private JFXCheckBox showBeer, showWine, showSpirits;

    @FXML
    private GridPane pane;

    private List<COLA> colasOnDisplay;


    private COLASearchHandler colaSearchHandler;

    public COLASearchController(){
        colaSearchHandler = new COLASearchHandler();
    }

    public void setSearchTerm(String searchTerm){
        this.searchTerm = searchTerm;
        searchField.setText(searchTerm);
    }

    public void search(ActionEvent e) {
        Platform.runLater(() -> search(searchField.getText()));
    }

    public void advancedSearch(){
    }

    public void search(String searchTerm) {
        this.searchTerm = searchTerm;

        populateList();
    }

    private void populateList(){
        colasOnDisplay= colaSearchHandler.filteredSearch(genFilter());


        SearchRanking maxRanking = new MaxSearchRanking(new FuzzyBrandNameRanking(searchTerm), new FuzzyFancifulNameRanking(searchTerm));

        colasOnDisplay.sort(Comparator.comparingInt(maxRanking::rank).reversed());

        searchList.getChildren().clear();

        if(colasOnDisplay.isEmpty()){
            VBox userListItem = new VBox();
            Label emptyLabel = new Label();
            emptyLabel.setText("No applications");
            userListItem.getChildren().add(emptyLabel);
            userListItem.getStyleClass().add("list-item-empty");
            searchList.getChildren().add(userListItem);
        }

        for(COLA c: colasOnDisplay){
            HBox applicationListItem = new HBox();
            VBox itemName = new VBox();
            itemName.setAlignment(Pos.CENTER_LEFT);
            itemName.setSpacing(16);
            ImageView typeImage = new ImageView();
            typeImage.setFitHeight(40);
            typeImage.setFitWidth(40);
            typeImage.prefWidth(40);
            typeImage.maxWidth(40);
            typeImage.minWidth(40);
            typeImage.setPreserveRatio(true);
            ImageUtils.centerImage(typeImage);

            String image = "";
            switch (c.getType()){
                case WINE:
                    image = "/images/wine.png";
                    break;
                case BEER:
                    image = "/images/beer.png";
                    break;
                case DISTILLEDSPIRITS:
                    image = "/images/spirits.png";
                    break;
            }

            typeImage.setImage(new Image(getClass().getResourceAsStream(image)));

            Label brandLabel = new Label(c.getBrandName());
            Label fancifulLabel = new Label();
            String labeltext = c.getFancifulName().trim();
            fancifulLabel.setText(labeltext);
            fancifulLabel.getStyleClass().add("subhead");
            itemName.getChildren().addAll(brandLabel, fancifulLabel);
            itemName.setPrefWidth(512);
            itemName.setPadding(new Insets(0, 0, 0, 8));

            VBox itemDesc = new VBox();
            itemDesc.setAlignment(Pos.CENTER_RIGHT);
            itemDesc.setSpacing(8);
            itemDesc.setPadding(new Insets(0, 16, 0, 0));
            Label serialLabel = new Label(String.format("Serial No. %s", c.getSerialNumber()));
            serialLabel.setFont(new Font(10));
            serialLabel.getStyleClass().add("subhead");

            Label dateLabel = new Label(String.format("Completed on %s", c.getApprovalDate().toString()));
            dateLabel.setFont(new Font(10));
            dateLabel.getStyleClass().add("subhead");

            Label idLabel = new Label(String.format("TTB ID #%d", c.getId()));
            idLabel.setFont(new Font(10));
            idLabel.getStyleClass().add("subhead");

            itemDesc.getChildren().addAll(serialLabel, dateLabel, idLabel);
            itemDesc.setPrefWidth(512);


            applicationListItem.getChildren().addAll(typeImage, itemName, itemDesc);
            applicationListItem.getStyleClass().add("list-item");

            applicationListItem.setOnMouseClicked(mouseEvent -> {
                UIManager.Page page = UIManager.getInstance().loadPage(UIManager.APPLICATION_DETAIL_PAGE);
                ApplicationDetailController controller = page.getController();
                controller.setSearchTerm(searchTerm);
                controller.setAlcohol(c);
                UIManager.getInstance().displayPage(searchList.getScene(), page);
            });

            searchList.getChildren().add(applicationListItem);
        }

        descriptionLabel.setText("Showing top " + colasOnDisplay.size() + " results for \"" + searchTerm + "\"");
        descriptionLabel.setVisible(true);
        saveBtn.setDisable(colasOnDisplay.size() == 0);

    }

    private SearchFilter genFilter(){

        SearchFilter filter = new FuzzyBrandNameFilter(searchTerm).or(new FuzzyFancifulNameFilter(searchTerm));

        filter = filter.and(new StatusFilter(ApplicationStatus.APPROVED));

        if (!showBeer.isSelected()){
            filter = filter.and(new TypeFilter(AlcoholType.BEER).not());
        }

        if(!showWine.isSelected()){
            filter = filter.and(new TypeFilter(AlcoholType.WINE).not());
        }

        if(!showSpirits.isSelected()){
            filter = filter.and(new TypeFilter(AlcoholType.DISTILLEDSPIRITS).not());
        }

        return filter;
    }

    public void filter(ActionEvent e) {
        Platform.runLater(() -> {
            search(e);
        });

    }

    public void saveTSV(ActionEvent e) {

//        ApplicationExporter exporter = new ApplicationExporter(new TSVExporter());
//        exporter.export(data);
    }

    public void saveUserChar(ActionEvent e) {
//        TextInputDialog dialog = new TextInputDialog();
//        dialog.setTitle("Data Exporter");
//        dialog.setHeaderText("Enter a character.");
//        dialog.setContentText("Please enter a character to separate data:");
//
//        Optional<String> result = dialog.showAndWait();
//        result.ifPresent(name -> {
//            ApplicationExporter exporter = new ApplicationExporter(new UserCharExporter(name.charAt(0), "txt"));
//            exporter.export(data);
//        });


    }

    public void saveCSV() {
        ExportApplicationsController controller = new ExportApplicationsController();
        controller.export(colasOnDisplay);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        JFXScrollPane.smoothScrolling(scrollPane);

        descriptionLabel.setVisible(false);

        pane.setOnKeyPressed(keyEvent -> {
            searchField.requestFocus();
        });

        searchField.onActionProperty().addListener(observable -> {
            Platform.runLater(() -> search(searchTerm));
        });

        showBeer.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            Platform.runLater(() -> search(searchTerm));
        });

        showWine.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            Platform.runLater(() -> search(searchTerm));
        });

        showSpirits.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            Platform.runLater(() -> search(searchTerm));
        });

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            Platform.runLater(() -> search(t1));
        });

        Platform.runLater(() -> {
            colaSearchHandler.receiveAllCOLAs();
            search(searchTerm);
        });
    }
}
