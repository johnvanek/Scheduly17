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
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

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

    @FXML
    private void displayNativeLanguage() throws UnsupportedEncodingException {
        String location = "Lang";

        Locale userLocale = Locale.getDefault();
        System.out.println("Current Local is " + userLocale);
        //Enable the lower line to translate to french
        //userLocale.setDefault(new Locale("fr", "France"));
        userLocale = Locale.getDefault();
        System.out.println("New Locale is " + userLocale);
        ResourceBundle languageDictionary = ResourceBundle.getBundle(location, userLocale);

        if (userLocale.getLanguage() == "en" || userLocale.getLanguage().equals("fr")){
            //If there is a match get the keys
            String UserNameText = languageDictionary.getString("UserNameText");
            String PassWordText = languageDictionary.getString("PassWordText");
            String LoginButtonText = languageDictionary.getString("LoginButtonText");
            String ErrorHandlingText = languageDictionary.getString("ErrorHandlingText");
            String LoginPortalText = languageDictionary.getString("LoginPortalText");
            String RegionCodeText = languageDictionary.getString("RegionCode");
            //Then set the keys                                               ;
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

    @FXML
    boolean userIsValid() {
        return true;
    }

    @FXML
    void removeRedBordersUsername() {
        UserNameTextField.setStyle("-fx-border-color: #97CBFF");
    }

    @FXML
    void removeRedBordersPassWord() {
        PassWordTextField.setStyle("-fx-border-color: #97CBFF");
    }

    @FXML
    void releaseFocusFromTextBox() {
        FocusMe.requestFocus();
    }

    @FXML
    void loginUser() throws IOException {

        //Break this into several smaller functions remember the single principle.
        //Clean this code up tomorrow.
        //This is the button that is clicked when the user pressed the submit button

        // continue the to the next page and translate this back to NA-en
        //Here we have to do the request to the server then do something bases on an error code
        //For not just assume the user is alwaysInvalid

        if (!userIsValid()) {
            //If the user if valid we want to compare there check here with any appointments in the next 15 minutes
            //And send up and alert that says hey you have an appointment in w/e minutes from login

            Parent viewCalendar = FXMLLoader.load(getClass().getResource("../view/Calendar.fxml"));
            Stage primaryStage = (Stage) LoginButton.getScene().getWindow(); //This gets the primary Stage from the Button source
            //However will probably need to persist some data or send some data with the user across this screen
            primaryStage.setScene(new Scene(viewCalendar)); // This will send the user to the next screen
            //So far this is working as intended and is taking the user to the next screen.
        } else {
            //They must not be in the database this must be a wrong username or password
            displayErrorCodeStyling();
        }
    }

    @FXML
    private void undoErrorStyling() {
        ErrorNotificationText.setOpacity(0);
    }

    //********************************************************************//
    //Regular Functions
    //********************************************************************//

    private void displayErrorCodeStyling() {
        ErrorNotificationText.setOpacity(1);
        UserNameTextField.clear();
        PassWordTextField.clear();
        releaseFocusFromTextBox();
        makeRedBorders();
        errorCodeAnimation();
    }

    private void makeRedBorders() {
        UserNameTextField.setStyle("-fx-border-color: red");
        PassWordTextField.setStyle("-fx-border-color: red");
    }

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

