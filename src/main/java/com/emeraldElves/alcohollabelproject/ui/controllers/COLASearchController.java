package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.data.COLASearchHandler;
import com.emeraldElves.alcohollabelproject.data.search.*;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.emeraldElves.alcohollabelproject.ui.controllers.ApplicationDetailController;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.database.Storage;
import com.jfoenix.controls.JFXScrollPane;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.apache.commons.lang3.StringEscapeUtils;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.time.LocalDate;
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

    private COLASearchHandler colaSearchHandler;

    public COLASearchController(){
        colaSearchHandler = new COLASearchHandler();
    }

    public void setSearchTerm(String searchTerm){
        this.searchTerm = searchTerm;
        searchField.setText(searchTerm);
//        search(searchTerm);
    }

    public void search(ActionEvent e) {
        Platform.runLater(() -> search(searchField.getText()));
    }

    public void advancedSearch(){
//        main.loadFXML("/fxml/AdvancedSearchPage.fxml");
    }

    public void search(String searchTerm) {
        this.searchTerm = searchTerm;

        populateList();
    }

    private void populateList(){
        List<COLA> colas = colaSearchHandler.filteredSearch(genFilter());

        searchList.getChildren().clear();

        if(colas.isEmpty()){
            VBox userListItem = new VBox();
            Label emptyLabel = new Label();
            emptyLabel.setText("No applications");
            userListItem.getChildren().add(emptyLabel);
            userListItem.getStyleClass().add("list-item-empty");
            searchList.getChildren().add(userListItem);
        }

        for(COLA c: colas){
            HBox applicationListItem = new HBox();
            VBox itemName = new VBox();
            itemName.setAlignment(Pos.CENTER_LEFT);
            itemName.setSpacing(16);
            Label brandLabel = new Label(c.getBrandName());
            Label fancifulLabel = new Label();
            String labeltext = c.getFancifulName().trim();
            if(!labeltext.isEmpty()){
              labeltext += " ";
            }
            labeltext += String.format("(%s)", c.getType().getDisplayName());
            fancifulLabel.setText(labeltext);
            fancifulLabel.getStyleClass().add("subhead");
            itemName.getChildren().addAll(brandLabel, fancifulLabel);
            itemName.setPrefWidth(512);

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


            applicationListItem.getChildren().addAll(itemName, itemDesc);
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

        descriptionLabel.setText("Showing top " + colas.size() + " results for \"" + searchTerm + "\"");
        descriptionLabel.setVisible(true);
        saveBtn.setDisable(colas.size() == 0);

    }

    private SearchFilter genFilter(){

        SearchFilter filter = new FuzzyBrandNameFilter(searchTerm).and(new FuzzyFancifulNameFilter(searchTerm));

        filter = filter.or(new StatusFilter(ApplicationStatus.APPROVED));

//        if (filterBeers.isSelected()){
//            filter = filter.or(new TypeFilter(AlcoholType.BEER));
//        }
//
//        if(filterWine.isSelected()){
//            filter = filter.or(new TypeFilter(AlcoholType.WINE));
//        }
//
//        if(filterSpirits.isSelected()){
//            filter = filter.or(new TypeFilter(AlcoholType.DISTILLEDSPIRITS));
//        }

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

    public void saveCSV(ActionEvent e) {

//        ApplicationExporter exporter = new ApplicationExporter(new CSVExporter());
//        exporter.export(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        JFXScrollPane.smoothScrolling(scrollPane);

        descriptionLabel.setVisible(false);

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            Platform.runLater(() -> search(t1));
        });

        Platform.runLater(() -> {
            colaSearchHandler.receiveAllCOLAs();
            search(searchTerm);
        });
    }
}
