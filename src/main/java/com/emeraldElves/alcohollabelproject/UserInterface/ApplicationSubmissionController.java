package com.emeraldElves.alcohollabelproject.UserInterface;

import com.emeraldElves.alcohollabelproject.Data.AlcoholType;
import com.emeraldElves.alcohollabelproject.Data.ProductSource;
import com.emeraldElves.alcohollabelproject.IDGenerator2.IDGenerator.TTBIDGenerator;
import com.emeraldElves.alcohollabelproject.data.COLA;
import com.emeraldElves.alcohollabelproject.database.Storage;
import com.jfoenix.controls.*;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
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
    private JFXTextArea formulaText;

    @FXML
    private JFXButton uploadButton;

    @FXML
    private Label uploadLabelLocation;

    private List<ValidatorBase> validators;

    private long id;

    private File labelFile;

    public ApplicationSubmissionController(){
        validators = new LinkedList<>();
    }

    public void submit(){

//        if(!isFilledOut(brandName) || !isFilledOut(serialNumber)){
//            if(!isFilledOut(brandName)){
//                // Brand name required
//                Validator<JFXTextField> textFieldValidator = Vali
//            }
//
//            if(!isFilledOut(serialNumber)){
//                // Serial number required
//            }
//            return;
//        }

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
        String formula = formulaText.getText();

        double alcoholContentPercent = 0;
        try {
            alcoholContentPercent = Double.valueOf(alcoholContent.getText());
        } catch (Exception e){
            // EMPTY
        }

        COLA cola = new COLA(id, brand_name, alcoholType, serial_number, source);
        cola.setFancifulName(fanciful_name);
        cola.setAlcoholContent(alcoholContentPercent);
        cola.setFormula(formula);

        Storage.getInstance().saveCOLA(cola); // TODO: replace this with higher up storage mechanism

        System.out.println("Saved " + cola.toString());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serialNumber.setText(String.valueOf(LocalDate.now().getYear() % 100) + "XXXX");
        id = new TTBIDGenerator().generateID();
        ttbID.setText("TTB ID #" + String.valueOf(id));

        setRequired(brandName, "Brand name required");
        setNumericOnly(alcoholContent, "Must be a percentage");
        setRequiredLength(serialNumber, "Serial number must be 6 digits", 6);
        setAlphaNumericOnly(serialNumber, "Serial number must be alpha-numeric");

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

    private void setRequiredLength(JFXTextField textField, String message, int length){
        TextLengthValidator validator = new TextLengthValidator(length);
        validator.setMessage(message);

        textField.getValidators().add(validator);

        textField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                textField.validate();
            }
        });

        validators.add(validator);
    }

    private void setRequired(JFXTextField textField, String message){
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage(message);

        textField.getValidators().add(validator);

        textField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                textField.validate();
            }
        });

        validators.add(validator);
    }

    private void setAlphaNumericOnly(JFXTextField textField, String message){
        AlphaNumericValidator validator = new AlphaNumericValidator();
        validator.setMessage(message);

        textField.getValidators().add(validator);

        textField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                textField.validate();
            }
        });

        validators.add(validator);
    }

    private void setNumericOnly(JFXTextField textField, String message){
        OptionalDoubleValidator validator = new OptionalDoubleValidator();
        validator.setMessage(message);

        textField.getValidators().add(validator);

        textField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                textField.validate();
            }
        });

        validators.add(validator);
    }


    private boolean isFilledOut(JFXTextField textField){
        return !textField.getText().isEmpty();
    }

}
