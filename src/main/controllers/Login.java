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
    //This the source of the problem cant fin this resoruce
    private  URL resource = getClass().getClassLoader().getResource("Images/agendaWriting.mp4");
    private  String pathString = resource.toString();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ErrorCodeTextTest.setOpacity(1);
        //On Main Screen initialization do the following
        //Release the focus from the TextBox
        releaseFocusFromTextBox();
        //Platform.runLater(this::releaseFocusFromTextBox);

        //Set up the autoplay for the background video log-in-Screen
        MediaPlayer player = new MediaPlayer(new Media(pathString));
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE); //This will loop
        //player.play();
        VideoPlayer.setMediaPlayer(player);

        if (player != null) {
            System.out.println("This player is not null");
        }

        player.setOnError(()->
                System.out.println("media error"+player.getError().toString()));
        //Attempt at making this semi-responsive
        //This is the wrong architechture for this project
//        LoginBox.widthProperty().addListener(event -> {
//            //resizeMemberLoginText();
//        });
        //Set the users preferred language and Location From Computer Settings
        try {
            setUserLangLoc();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    //********************************************************************//
    //Fxml ID's
    //********************************************************************//


    @FXML
    private MediaView VideoPlayer;

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


    //********************************************************************//
    //Fxml Functions
    //********************************************************************//

    @FXML
    private void setUserLangLoc() throws UnsupportedEncodingException {
        //Bases on the System time settings set the language and the error codes for all the language Strings on this page
        //Dont think that I need this stuff
//        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

//        assert is != null;
        String location = "Lang"; //This is where the Resource Bundle is stored


        ResourceBundle resourceBundle = ResourceBundle.getBundle(location, Locale.getDefault());



        //If the users default language is set to en or fr show them this app login screen in that preferred language
        if(Locale.getDefault().getLanguage().equals("en")){
            //Do a conversion here for the japanese character set
            //Convert is to the correct encoding and then display in UTF-8 or else wont render properly
            //Despite telling intellij to set all properties to IS0-8859-1

            String LoginPortalText = resourceBundle.getString("LoginPortalText");
            String UserNameText = resourceBundle.getString("UserNameText");
            String PassWordText = resourceBundle.getString("PassWordText");
            String LoginButtonText = resourceBundle.getString("LoginButtonText");
            String ErrorHandlingText = resourceBundle.getString("ErrorHandlingText");


            //This is french shouldnt need the charsets
//                String jpLoginPortalText = new String(LoginPortalText.getBytes("ISO-8859-1"), "UTF-8");
//                String jpUserNameText = new String(UserNameText.getBytes("ISO-8859-1"), "UTF-8");
//                String jpPassWordText = new String(PassWordText.getBytes("ISO-8859-1"), "UTF-8");
//                String jpLoginButtonText = new String(LoginButtonText.getBytes("ISO-8859-1"), "UTF-8");
//                String jpErrorHandlingText = new String(ErrorHandlingText.getBytes("ISO-8859-1"), "UTF-8");
            MemberText.setText(LoginPortalText);
            UserNameTextField.setPromptText(UserNameText);
            PassWordTextField.setPromptText(PassWordText);
            LoginButton.setText(LoginButtonText);

            ErrorCodeTextTest.setText(ErrorHandlingText);
            //Only want to show this error code if exception has happened.

            System.out.println(Locale.getDefault().getCountry());
            //resizeMemberLoginText();

        }
    }

    @FXML
    boolean userIsValid(){
        //Set to false to test the errorHandling currently
        //Set to true to got the next Scene
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

    //********************************************************************//
    //Regular Functions
    //********************************************************************//
    void resizeMemberLoginText (){
        //This is not making it twitch something else is
//        double newFontSize = LoginBox.getWidth() / 9.5;
//        MemberText.setFont(Font.font(newFontSize));
    }


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
        TranslateTransition moveRight = new TranslateTransition(Duration.millis(100),LoginBox);
        moveRight.setFromX(0);
        moveRight.setToX(6);
        TranslateTransition overShootLeft = new TranslateTransition(Duration.millis(80),LoginBox);
        overShootLeft.setFromX(6);
        overShootLeft.setToX(-10);
        SequentialTransition sequentialTransition = new SequentialTransition(moveRight,overShootLeft);
        sequentialTransition.play();
    }
}

