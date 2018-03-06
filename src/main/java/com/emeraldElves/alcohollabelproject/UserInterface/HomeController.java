package com.emeraldElves.alcohollabelproject.UserInterface;


import com.emeraldElves.alcohollabelproject.Authenticator;
import com.emeraldElves.alcohollabelproject.COLASearch;
import com.emeraldElves.alcohollabelproject.Data.*;
import com.emeraldElves.alcohollabelproject.data.COLA;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.util.*;

/**
 * Created by Harry and Joe on 4/2/2017.
 */
public class HomeController implements IController {
    public ArrayList<Label> mostRecentLabels;
    public ArrayList<SubmittedApplication> mostRecentSubmissions;
    public List<SubmittedApplication> submitted;


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

    private Main main;

    @FXML
    private TextField searchbox;


    // delete this
    @FXML
    private Button goToProfile;

    private COLASearch search;

    private List<COLA> alcohol;

    public void aboutProject(){
        main.loadFXML("/fxml/AboutPage.fxml");
    }
    public HomeController() {
        mostRecentLabels = new ArrayList<>();
        mostRecentSubmissions = new ArrayList<>();
        search = new COLASearch();
        submitted = search.searchRecentApplications(4);
//        COLA info = new COLA(0, "Drunk", AlcoholType.WINE, "12345678", ProductSource.DOMESTIC);
//        info.setAlcoholContent(50);
//        info.setFancifulName("Drug$");
//        Main.storage.saveCOLA(info);
        alcohol = Main.storage.getAllCOLAs();
    }


    // TODO: put FXML in correct folder

    /**
     * Loads homepage
     */

    public void utilityButton() {
        switch (Authenticator.getInstance().getUserType()) {
            case TTBAGENT:
                main.loadFXML("/fxml/TTBWorkflowPage.fxml");
                break;
            case APPLICANT:
                main.loadFXML("/fxml/ApplicantWorkflowPage.fxml");
                break;
        }
    }

    public void loadLog() {
        switch (Authenticator.getInstance().getUserType()) {
            case TTBAGENT:
                Authenticator.getInstance().logout();
                main.loadHomepage();
                break;
            case APPLICANT:
                Authenticator.getInstance().logout();
                main.loadHomepage();
                break;
            case SUPERAGENT:
                Authenticator.getInstance().logout();
                main.loadHomepage();
                break;
            case BASIC:
                main.loadFXML("/fxml/Login.fxml");
                break;
        }
    }

    public void loadProfile() {
        main.loadFXML("/fxml/ProfilePage.fxml");
    }

    public void createNewUser(){
        main.loadFXML("/fxml/NewUser.fxml");
    }


    public void searchDatabase() {
        main.loadFXML("/fxml/Search.fxml",searchbox.getText());
    }

    public void feelingThirsty() {
        List<SubmittedApplication> applications = search.searchApprovedApplications(false, false, false);
        Random random = new Random();
        SubmittedApplication application;
        if (applications.isEmpty()) {
            application = null;
        } else {
            int pos = random.nextInt(applications.size());
            application = applications.get(pos);
        }
        if (application != null)
            main.loadFXML("/fxml/DetailedSearchPage.fxml", application, application.getApplication().getAlcohol().getBrandName());
    }

    public void init(Bundle bundle){
        this.init(bundle.getMain("main"));
    }

    public void init(Main main) {
        this.main = main;
        for (int i = 0; i < alcohol.size(); i++) {
            COLA recentApplication = alcohol.get(i);
            switch (i) {
                case 0:
                    alc1.setOnMouseClicked(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ApplicationDetailPage.fxml"));
                            Parent root = loader.load();
                            ApplicationDetailController controller = loader.getController();
                            controller.setAlcohol(recentApplication);
                            alc1.getScene().setRoot(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    brand1.setText(recentApplication.getBrandName().toUpperCase());
                    fanciful1.setText(recentApplication.getFancifulName());
                    content1.setText(recentApplication.getAlcoholContent() + "%");
                    date1.setText(recentApplication.getApprovalDate().toString());
                    image1.setImage(recentApplication.getLabelImage().display());
                    ImageUtils.centerImage(image1);
                    break;
                case 1:
                    alc2.setOnMouseClicked(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ApplicationDetailPage.fxml"));
                            Parent root = loader.load();
                            ApplicationDetailController controller = loader.getController();
                            controller.setAlcohol(recentApplication);
                            alc1.getScene().setRoot(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });                    brand2.setText(recentApplication.getBrandName().toUpperCase());
                    fanciful2.setText(recentApplication.getFancifulName());
                    content2.setText(recentApplication.getAlcoholContent() + "%");
                    date2.setText(recentApplication.getApprovalDate().toString());
                    image2.setImage(recentApplication.getLabelImage().display());
                    ImageUtils.centerImage(image2);
                    break;
                case 2:
                    alc3.setOnMouseClicked(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ApplicationDetailPage.fxml"));
                            Parent root = loader.load();
                            ApplicationDetailController controller = loader.getController();
                            controller.setAlcohol(recentApplication);
                            alc1.getScene().setRoot(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });                    brand3.setText(recentApplication.getBrandName().toUpperCase());
                    fanciful3.setText(recentApplication.getFancifulName());
                    content3.setText(recentApplication.getAlcoholContent() + "%");
                    date3.setText(recentApplication.getApprovalDate().toString());
                    image3.setImage(recentApplication.getLabelImage().display());
                    ImageUtils.centerImage(image3);
                    break;
                case 3:
                    alc4.setOnMouseClicked(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ApplicationDetailPage.fxml"));
                            Parent root = loader.load();
                            ApplicationDetailController controller = loader.getController();
                            controller.setAlcohol(recentApplication);
                            alc1.getScene().setRoot(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });                    brand4.setText(recentApplication.getBrandName().toUpperCase());
                    fanciful4.setText(recentApplication.getFancifulName());
                    content4.setText(recentApplication.getAlcoholContent() + "%");
                    date4.setText(recentApplication.getApprovalDate().toString());
                    image4.setImage(recentApplication.getLabelImage().display());
                    ImageUtils.centerImage(image4);
                    break;
            }
            List<SubmittedApplication> resultsList = search.searchApprovedApplications(false, false, false);

            AutoCompletionBinding<String> autoCompletionBinding;
            Set<String> possibleSuggestions = new HashSet<>();
            possibleSuggestions.clear();
            resultsList.sort((lhs, rhs) -> lhs.getApplication().getAlcohol().getBrandName().compareToIgnoreCase(rhs.getApplication().getAlcohol().getBrandName()));

            for (SubmittedApplication application : resultsList) {
                possibleSuggestions.add(application.getApplication().getAlcohol().getBrandName());
                possibleSuggestions.add(application.getApplication().getAlcohol().getFancifulName());
            }

            autoCompletionBinding = TextFields.bindAutoCompletion(searchbox, possibleSuggestions);
            autoCompletionBinding.setPrefWidth(472);

            searchbox.setOnKeyPressed(ke -> autoCompletionBinding.setUserInput(searchbox.getText().trim()));
        }

    }
}
