package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.Data.ApplicationStatus;
import com.emeraldElves.alcohollabelproject.data.COLASearchHandler;
import com.emeraldElves.alcohollabelproject.data.search.*;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.emeraldElves.alcohollabelproject.ui.controllers.ApplicationDetailController;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.database.Storage;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private AutoCompletionBinding<String> autoCompletionBinding;
    private Set<String> possibleSuggestions = new HashSet<>();
    @FXML
    private TextField searchField;
    @FXML
    private TableView<COLA> resultsTable;
    @FXML
    private TableColumn<COLA, String> dateCol;
    @FXML
    private TableColumn<COLA, String> manufacturerCol;
    @FXML
    private TableColumn<COLA, String> brandCol;
    @FXML
    private TableColumn<COLA, String> typeCol;
    @FXML
    private TableColumn<COLA, String> contentCol;
    @FXML
    private Button saveBtn;
    @FXML
    private MenuItem contextSaveBtn;
    @FXML
    private Label descriptionLabel;
    @FXML
    private CheckMenuItem filterBeers;
    @FXML
    private CheckMenuItem filterWine;
    @FXML
    private CheckMenuItem filterSpirits;

    private ObservableList<COLA> data = FXCollections.observableArrayList();

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
        //Remove previous results
        data.clear();

        this.searchTerm = searchTerm;

        SearchFilter filter = genFilter();

        data.addAll(colaSearchHandler.filteredSearch(filter));

        //Find & add matching applications
        descriptionLabel.setText("Showing top " + data.size() + " results for \"" + searchTerm + "\"");
        descriptionLabel.setVisible(true);
        saveBtn.setDisable(data.size() == 0);
        contextSaveBtn.setDisable(data.size() == 0);
    }

    private SearchFilter genFilter(){

        SearchFilter filter = new FuzzyBrandNameFilter(searchTerm).and(new FuzzyFancifulNameFilter(searchTerm));

        filter = filter.or(new StatusFilter(ApplicationStatus.APPROVED));

        if (filterBeers.isSelected()){
            filter = filter.or(new TypeFilter(AlcoholType.BEER));
        }

        if(filterWine.isSelected()){
            filter = filter.or(new TypeFilter(AlcoholType.WINE));
        }

        if(filterSpirits.isSelected()){
            filter = filter.or(new TypeFilter(AlcoholType.DISTILLEDSPIRITS));
        }

        return filter;
    }

    private void refreshSuggestions() {
        possibleSuggestions.clear();

        for(COLA app: colaSearchHandler.filteredSearch(null)){//new StatusFilter(ApplicationStatus.APPROVED))){
            possibleSuggestions.add(app.getBrandName());
            possibleSuggestions.add(app.getFancifulName());
        }

        if(autoCompletionBinding != null){
            autoCompletionBinding.dispose();
        }

        autoCompletionBinding = TextFields.bindAutoCompletion(searchField, possibleSuggestions);
        autoCompletionBinding.setPrefWidth(searchField.getPrefWidth() - 32);

    }

    public void filter(ActionEvent e) {
        Platform.runLater(() -> {
            refreshSuggestions();
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

        dateCol.setCellValueFactory(p -> {
            LocalDate date = p.getValue().getApprovalDate();
            return new ReadOnlyObjectWrapper<>(StringEscapeUtils.escapeJava(date.toString()));
        });

        manufacturerCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(StringEscapeUtils.escapeJava(p.getValue().getFancifulName())));

        brandCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(StringEscapeUtils.escapeJava(p.getValue().getBrandName())));

        typeCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(StringEscapeUtils.escapeJava(p.getValue().getType().name())));

        contentCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(StringEscapeUtils.escapeJava(String.format("%.1f%%", p.getValue().getAlcoholContent()))));

        resultsTable.setItems(data);
        resultsTable.setRowFactory(tv -> {
            TableRow<COLA> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    COLA rowData = row.getItem();
                    UIManager.Page page = UIManager.getInstance().loadPage(UIManager.APPLICATION_DETAIL_PAGE);
                    ApplicationDetailController controller = page.getController();
                    controller.setAlcohol(rowData);
                    UIManager.getInstance().displayPage(resultsTable.getScene(), page);
                }
            });
            return row;
        });

        descriptionLabel.setVisible(false);

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            Platform.runLater(() -> search(t1));
        });

        Platform.runLater(() -> {
//            allApps = Storage.getInstance().getAllCOLAs();
            colaSearchHandler.receiveAllCOLAs();
            search(searchTerm);
            refreshSuggestions();
        });
    }
}
