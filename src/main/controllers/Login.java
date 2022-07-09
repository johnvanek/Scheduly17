package main.controllers;


import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import main.utils.MediaManager;
import main.utils.StageManager;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static main.utils.LanguageManager.getLocale;
import static main.utils.LanguageManager.getResourceBundle;

/**
 * The controller for the Login-Screen View.
 */
public class Login implements Initializable {


    //***********//
    //Fxml ID's
    //***********//

    @FXML
    private AnchorPane LoginScreen;
    @FXML
    private Text RegionCode;
    @FXML
    private MediaView VideoPlayer;
    @FXML
    private AnchorPane LoginHolder;
    @FXML
    private Text MemberText;
    @FXML
    private TextField UserNameTextField;
    @FXML
    private PasswordField PassWordTextField;
    @FXML
    private TextFlow ErrorHandlingTextCode;
    @FXML
    private Label ErrorCodeLabel;
    @FXML
    private Text ErrorNotificationText;
    @FXML
    private GridPane LoginBox;
    @FXML
    private ImageView FocusMe;
    @FXML
    private Button LoginButton;
    @FXML
    private BorderPane MemberIconContainer;
    @FXML
    private ImageView MembersIcon;
    @FXML
    private BorderPane LockImageContainer;

    /**
     * Initializes the Login Screen is called after the FXML Fields have been loaded and injected.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Get the cursor focus out of the TextField Username.
        Platform.runLater(this::releaseFocusFromTextBox);
        //Set up the Media running on the Login Screen
        MediaManager.initMediaPlayer();
        VideoPlayer.setMediaPlayer(MediaManager.getMediaPlayer());
        //Display the Language Text
        displayLocaleLanguage();
    }

    //********************************************************************//
    //Fxml Methods
    //********************************************************************//

    /**
     * Display's the Login-Form in the User's language set from the Window's settings System Default.
     */
    @FXML
    public void displayLocaleLanguage() {


        //Testing log statements
        System.out.println("****************************************************");
        System.out.println("****************************************************");
        System.out.println("         Start-Testing Local Region Settings        ");
        System.out.println();
        System.out.println("Current Local -> " + getLocale());

        //If in English or French set the fields to the values in the language resources
        if (getLocale().getLanguage().equals("en") || getLocale().getLanguage().equals("fr")) {
            //Set the fields
            setLanguageValueFields(getResourceBundle(), getLocale());

            System.out.println("The language -> " + Locale.getDefault().getLanguage());
            System.out.println("The country -> " + Locale.getDefault().getCountry());
            System.out.println();
            System.out.println("               End-Testing-Locale                   ");
            System.out.println("****************************************************");
            System.out.println("****************************************************");
        }
    }

    /**
     * Validates whether the user is Valid.
     *
     * @return Returns a boolean value representing whether the user is or is not Valid.
     */
    @FXML
    private boolean userIsValid() {
        //Todo implement the functionality here make sure the user is valid.
        return true;
    }

    /**
     * Resets the red borders from the username text-field to the original color.
     */
    @FXML
    private void removeRedBordersUsername() {
        UserNameTextField.setStyle("-fx-border-color: #97CBFF");
    }

    /**
     * Resets the red borders from the password text-field to the original color.
     */
    @FXML
    private void removeRedBordersPassWord() {
        PassWordTextField.setStyle("-fx-border-color: #97CBFF");
    }

    /**
     * Takes the focus from any object currently in focus and gives it to an image-view upper left. So that the focus
     * does not start and is removed from the text-fields.
     */
    @FXML
    private void releaseFocusFromTextBox() {
        FocusMe.requestFocus();
    }

    /**
     * Attempts to Login the user.
     *
     * @throws IOException Signals an I/O exception of some sort has occurred.
     */
    @FXML
    public void loginUser() throws IOException {
        //Make this work later against the database.
        if (userIsValid()) {
            // Todo call the method here to check in the next 15 minutes perhaps at an interval.
            StageManager.setScene("appointments");
        } else {
            displayErrorCodeStyling();
        }
    }

    /**
     * Hides the error notification Text.
     */
    @FXML
    private void hideErrorText() {
        ErrorNotificationText.setOpacity(0);
    }

    //***************
    //Regular Methods
    //***************

    /**
     * Displays red color scheme And error text with a shaking animation to alert the user.
     */
    public void displayErrorCodeStyling() {
        ErrorNotificationText.setOpacity(1);
        UserNameTextField.clear();
        PassWordTextField.clear();
        releaseFocusFromTextBox();
        makeRedBorders();
        errorCodeAnimation();
    }

    private void setLanguageValueFields(ResourceBundle dictionary, Locale locale) {
        //The main labels and Text
        String UserNameText = dictionary.getString("UserNameText");
        String PassWordText = dictionary.getString("PassWordText");
        String LoginButtonText = dictionary.getString("LoginButtonText");
        String ErrorHandlingText = dictionary.getString("ErrorHandlingText");
        String LoginPortalText = dictionary.getString("LoginPortalText");
        String RegionCodeText = dictionary.getString("RegionCode");
        //The prompt text and misc text.
        MemberText.setText(LoginPortalText);
        PassWordTextField.setPromptText(PassWordText);
        LoginButton.setText(LoginButtonText);
        ErrorNotificationText.setText(ErrorHandlingText);
        UserNameTextField.setPromptText(UserNameText);

        //RegionCode in the upper left
        RegionCode.setText(RegionCodeText + " " + locale.getCountry());
    }

    /**
     * Makes the borders for the username and password text fields red.
     */
    private void makeRedBorders() {
        UserNameTextField.setStyle("-fx-border-color: red");
        PassWordTextField.setStyle("-fx-border-color: red");
    }

    /**
     * Shakes the login box left to right. Normally called on an error.
     */
    private void errorCodeAnimation() {
        //Animation for a Right wiggle effect
        //Shake the entire login-box and then make the username and text-field red
        TranslateTransition moveRight = new TranslateTransition(Duration.millis(100), LoginBox);
        moveRight.setFromX(0);
        moveRight.setToX(6);
        TranslateTransition overShootLeft = new TranslateTransition(Duration.millis(80), LoginBox);
        overShootLeft.setFromX(6);
        overShootLeft.setToX(-10);
        SequentialTransition sequentialTransition = new SequentialTransition(moveRight, overShootLeft);
        sequentialTransition.play();
    }
}

