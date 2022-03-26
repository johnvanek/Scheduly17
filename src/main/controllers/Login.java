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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {
    //***************//
    //Resource Path
    //***************//

    //This might be the cause of the null pointer exception right here check back if stumped.
    //This the source of the problem
    private static final URL resource = Login.class.getResource("/Images/notesWriting.mp4");
    private static final String pathString = resource.toString();
    @FXML
    private MediaView VideoPlayer;


    //********************************************************************//
    //Fxml ID's
    //********************************************************************//
    @FXML
    private AnchorPane LoginScreen;
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
    private Text ErrorCodeTextTest;
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
        //This is probably the runtime computer slowing this down.
        player.setCycleCount(MediaPlayer.INDEFINITE);
        VideoPlayer.setMediaPlayer(player);


        if (player != null) {
            System.out.println("This player is not null");
        }
        try {
            displayNativeLanguage();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    //********************************************************************//
    //Fxml Functions
    //********************************************************************//

    @FXML
    private void displayNativeLanguage() throws UnsupportedEncodingException {
        //The resource-Bundle will set the location to what is on the user's locale windows computer settings.
        //Do not need to do an if statement for locale if all are the same key.

        String location = "Lang";
        ResourceBundle resourceBundle = ResourceBundle.getBundle(location, Locale.getDefault());

        String UserNameText = resourceBundle.getString("UserNameText");
        String PassWordText = resourceBundle.getString("PassWordText");
        String LoginButtonText = resourceBundle.getString("LoginButtonText");
        String ErrorHandlingText = resourceBundle.getString("ErrorHandlingText");
        String LoginPortalText = resourceBundle.getString("LoginPortalText");

        MemberText.setText(LoginPortalText);
        UserNameTextField.setPromptText(UserNameText);
        PassWordTextField.setPromptText(PassWordText);
        LoginButton.setText(LoginButtonText);
        ErrorCodeTextTest.setText(ErrorHandlingText);

        System.out.println(Locale.getDefault().getCountry());
    }

    @FXML
    boolean userIsValid() {
        //Set to false to test the errorHandling currently
        //Set to true to go the next Scene
        return true;
    }

    @FXML
    void RemoveRedBordersUsername() {
        UserNameTextField.setStyle("-fx-border-color: #97CBFF");
    }

    @FXML
    void RemoveRedBordersPassWord() {
        PassWordTextField.setStyle("-fx-border-color: #97CBFF");
    }

    @FXML
    void releaseFocusFromTextBox() {
        FocusMe.requestFocus();
    }

    @FXML
    void loginUser() throws IOException {
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
            ErrorCodeAlert();
        }
    }

    @FXML
    private void UndoErrorStyling() {
        ErrorCodeTextTest.setOpacity(0);
    }

    //********************************************************************//
    //Regular Functions
    //********************************************************************//


    private void ErrorCodeAlert() {
        //Show the ErrorCode Message
        ErrorCodeTextTest.setOpacity(1);
        //Clear and give the TextBox a red border
        UserNameTextField.clear();
        PassWordTextField.clear();
        releaseFocusFromTextBox(); //Also Release focus if haven't already done so.
        makeRedBorders();
        ErrorCodeAnimation();
    }

    private void makeRedBorders() {
        //Could toggle classes in javascript not sure how to do that here
        //System.out.println("The username TextField has this style Class" + UserNameTextField.getStyleClass());
        //This works as inline css
        UserNameTextField.setStyle("-fx-border-color: red");
        PassWordTextField.setStyle("-fx-border-color: red");
    }

    private void ErrorCodeAnimation() {
        //Animation for a Right wiggle effect
        //want to shake the entire login-box and then make the username and textfield red
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

