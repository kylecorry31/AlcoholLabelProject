package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Authenticator;
import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.Data.ProductSource;
import com.emeraldElves.alcohollabelproject.Data.ProxyLabelImage;
import com.emeraldElves.alcohollabelproject.Data.UserType;
import com.emeraldElves.alcohollabelproject.COLASubmissionHandler;
import com.emeraldElves.alcohollabelproject.data.User;
import com.emeraldElves.alcohollabelproject.ui.DialogFileSelector;
import com.emeraldElves.alcohollabelproject.ui.validation.*;
import com.emeraldElves.alcohollabelproject.ui.UIManager;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ApplicationSubmissionController implements Initializable{

    @FXML
    private JFXTextField brandName;

    @FXML
    private JFXTextField serialNumber;

    @FXML
    private JFXTextField fancifulName;

    @FXML
    private JFXTextField alcoholContent;

    @FXML
    private JFXTextField winePH, wineVintageYear;

    @FXML
    private Label ttbID;

    @FXML
    private JFXButton submit;

    @FXML
    private JFXRadioButton beerRadio;

    @FXML
    private JFXRadioButton wineRadio;

    @FXML
    private JFXRadioButton spiritRadio;

    @FXML
    private JFXRadioButton domesticRadio;

    @FXML
    private JFXRadioButton importedRadio;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private JFXTextField formulaText;

    @FXML
    private JFXButton uploadButton;

    @FXML
    private Label uploadLabelLocation;

    private List<ValidatorBase> validators;

    private long id;

    private File labelFile;

    private long applicantID;

    private COLASubmissionHandler colaSubmissionHandler;

    private User user;

    public ApplicationSubmissionController(){
        validators = new LinkedList<>();
        colaSubmissionHandler = new COLASubmissionHandler();
    }

    public void submit(){

        boolean hasError = false;

        brandName.validate();
        serialNumber.validate();
        alcoholContent.validate();
        fancifulName.validate();

        for (ValidatorBase validator: validators){
            hasError = hasError || validator.getHasErrors();
        }

        if(hasError){
            return;
        }


        String brand_name = brandName.getText();
        String serial_number = serialNumber.getText();
        String fanciful_name = fancifulName.getText();
        AlcoholType alcoholType = getAlcoholType();
        ProductSource source = getProductSource();

        double alcoholContentPercent = 0;
        try {
            alcoholContentPercent = Double.valueOf(alcoholContent.getText());
        } catch (Exception e){
            // EMPTY
        }

        COLA cola = new COLA(id, brand_name, alcoholType, serial_number, source);
        cola.setFancifulName(fanciful_name);
        cola.setAlcoholContent(alcoholContentPercent);
        cola.setApplicantID(applicantID);

        try {
            long formula = Long.valueOf(formulaText.getText());
            cola.setFormula(formula);
        } catch (Exception e){
            // EMPTY
        }
        if(alcoholType == AlcoholType.WINE){
            try {
                double winePh = Double.valueOf(winePH.getText());
                cola.setWinePH(winePh);
            } catch (Exception e){
                // EMPTY
            }

            try {
                int year = Integer.valueOf(wineVintageYear.getText());
                cola.setVintageYear(year);
            } catch (Exception e){
                // EMPTY
            }
        }

        if(labelFile != null) {
            Path imageSrc = Paths.get((labelFile.getPath()));
            Path targetDir = Paths.get("Labels");
            try {
                Files.createDirectories(targetDir);//in case target directory didn't exist
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fileName = String.format("%d.%s", id, FilenameUtils.getExtension(labelFile.getAbsolutePath()));
            Path target = targetDir.resolve(fileName);
            try {
                Files.copy(imageSrc, target, StandardCopyOption.REPLACE_EXISTING);
                cola.setLabelImage(new ProxyLabelImage(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        colaSubmissionHandler.submitCOLA(cola);

        UIManager.getInstance().displayPage(brandName.getScene(), UIManager.HOME_PAGE);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        user = Authenticator.getInstance().getUser();

        if(user.getType() != UserType.APPLICANT){
            UIManager.getInstance().displayPage(brandName.getScene(), UIManager.HOME_PAGE);
        }

        applicantID = user.getId();

        winePH.managedProperty().bind(winePH.visibleProperty());
        wineVintageYear.managedProperty().bind(wineVintageYear.visibleProperty());

        winePH.visibleProperty().bind(wineRadio.selectedProperty());
        wineVintageYear.visibleProperty().bind(wineRadio.selectedProperty());

        wineRadio.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            winePH.clear();
            wineVintageYear.clear();
        });

        serialNumber.setText(colaSubmissionHandler.getNextSerialNumber(user));

        id = colaSubmissionHandler.getNextCOLAID();
        ttbID.setText("TTB ID #" + String.valueOf(id));

        addValidator(brandName, new RequiredFieldValidator(), "Brand name required");
        addValidator(alcoholContent, new OptionalDoubleValidator(), "Must be a percentage");
        addValidator(serialNumber, new TextLengthValidator(6), "Serial number must be 6 digits");
        addValidator(serialNumber, new AlphaNumericValidator(), "Serial number must be alpha-numeric");
        addValidator(winePH, new OptionalDoubleValidator(), "Must be a number");
        addValidator(wineVintageYear, new OptionalYearValidator(), "Must be a valid year (YYYY)");
        addValidator(formulaText, new OptionalNumberValidator(), "Must be a number");

        JFXScrollPane.smoothScrolling(scrollPane);

        submit.setOnMouseClicked(mouseEvent -> submit());

        uploadButton.setOnMouseClicked(mouseEvent -> uploadImage());
    }

    private void uploadImage(){
        DialogFileSelector fileSelector = new DialogFileSelector();
        labelFile = fileSelector.openFile("Image files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        if (labelFile != null){
            uploadLabelLocation.setText(labelFile.getAbsolutePath());
        } else {
            uploadLabelLocation.setText("No label selected");
        }
    }

    private AlcoholType getAlcoholType(){
        if(beerRadio.isSelected())
            return AlcoholType.BEER;
        if(wineRadio.isSelected())
            return AlcoholType.WINE;
        return AlcoholType.DISTILLEDSPIRITS;
    }

    private ProductSource getProductSource(){
        if(domesticRadio.isSelected())
            return ProductSource.DOMESTIC;
        return ProductSource.IMPORTED;
    }

    private <T extends TextField & IFXTextInputControl> void addValidator(T textField, ValidatorBase validator, String message){
        validator.setMessage(message);

        textField.getValidators().add(validator);

        textField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                textField.validate();
            }
        });

        validators.add(validator);
    }
}
