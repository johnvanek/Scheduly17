package main.controllers;


import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.utils.StageManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The controller for the Login-Screen View.
 */
public class Login implements Initializable {

    private static final URL resource = Login.class.getResource("/Images/notesWriting.mp4");
    private static final String pathString = resource.toString();

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
     * Initializes the application is called after the FXML Fields have been loaded and injected.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(this::releaseFocusFromTextBox);
        MediaPlayer player = new MediaPlayer(new Media(pathString));
        player.isMute();
        player.setAutoPlay(true);
        //Runtime computer is probably not enough to run this indefinitely.
        player.setCycleCount(MediaPlayer.INDEFINITE);
        VideoPlayer.setMediaPlayer(player);

        try {
            displayNativeLanguage();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //********************************************************************//
    //Fxml Methods
    //********************************************************************//

    /**
     * Display's the Login-Form in the User's preferred language from their Window's settings.
     *
     * @throws UnsupportedEncodingException
     */
    @FXML
    public void displayNativeLanguage() throws UnsupportedEncodingException {
        String location = "Lang";
        Locale userLocale = Locale.getDefault();
        System.out.println("Current Local is " + userLocale);
        //Enable the lower line to translate to French
        userLocale.setDefault(new Locale("fr", "France"));
        userLocale = Locale.getDefault();
        System.out.println("New Locale is " + userLocale);
        ResourceBundle languageDictionary = ResourceBundle.getBundle(location, userLocale);

        if (userLocale.getLanguage() == "en" || userLocale.getLanguage().equals("fr")) {
            //If there is a match get the keys
            String UserNameText = languageDictionary.getString("UserNameText");
            String PassWordText = languageDictionary.getString("PassWordText");
            String LoginButtonText = languageDictionary.getString("LoginButtonText");
            String ErrorHandlingText = languageDictionary.getString("ErrorHandlingText");
            String LoginPortalText = languageDictionary.getString("LoginPortalText");
            String RegionCodeText = languageDictionary.getString("RegionCode");
            //Then set the keys
            MemberText.setText(LoginPortalText);
            PassWordTextField.setPromptText(PassWordText);
            LoginButton.setText(LoginButtonText);
            ErrorNotificationText.setText(ErrorHandlingText);
            UserNameTextField.setPromptText(UserNameText);

            //RegionCode is combination of two strings
            RegionCode.setText(RegionCodeText + " " + userLocale.getCountry());

            //Confirmation of Locale geo in console. -> Check/Test in windows settings.
            //Time and language settings and Region Settings.
            System.out.println("The language is " + Locale.getDefault().getLanguage());
            System.out.println("This country is " + Locale.getDefault().getCountry());
        }
    }

    /**
     * Validates whether the user is Valid.
     * Compares the Values supplied to etc.. continue this later
     *
     * @return Returns a boolean value representing whether the user is or is not Valid.
     */
    @FXML
    private boolean userIsValid() {
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
     * Takes the focus from any object currently in focus and gives it to an image-view upper left.
     */
    @FXML
    private void releaseFocusFromTextBox() {
        FocusMe.requestFocus();
    }

    /**
     * Attempts to Login the user.
     *
     * @throws IOException
     */
    @FXML
    public void loginUser() throws IOException {
        //Make this work later against the database.
        if (userIsValid()) {
            //If the user if valid we want to compare there check here with any appointments in the next 15 minutes
            //And send up and alert that says hey you have an appointment in w/e minutes from login
            System.out.println(StageManager.getPrimaryStage());
            StageManager.setScene("appointments");
        } else {
            //They must not be in the database this must be a wrong username or password
            displayErrorCodeStyling();
        }
    }

    /**
     * Hides the error notification Text.
     */
    @FXML
    private void undoErrorStyling() {
        ErrorNotificationText.setOpacity(0);
    }

    //********************************************************************//
    //Regular Functions
    //********************************************************************//

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

    /**
     * Makes the borders for the username and password text fields red.
     */
    private void makeRedBorders() {
        UserNameTextField.setStyle("-fx-border-color: red");
        PassWordTextField.setStyle("-fx-border-color: red");
    }

    /**
     * Shakes the login box left to right.
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

