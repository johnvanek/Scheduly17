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
import main.DAO.models.User;
import main.database.Connection;
import main.utils.MediaManager;
import main.utils.ObservableManager;
import main.utils.StageManager;
import main.utils.TimeManager;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import static main.utils.LanguageManager.getLocale;
import static main.utils.LanguageManager.getResourceBundle;

/**
 * This class represents the controller logic for the view of the same name. This is the entry point
 * of the application for the user.
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

    //********************************************************************//
    //Fxml Methods
    //********************************************************************//


    /**
     * Public constructor for login only public so that javafx can instantiate it.
     */
    public Login() {
    }

    /**
     * Display's the Login-Form in the User's language set from the Window's settings System Default.
     * Using the currently supported Resource bundle the only included properties are currently (en and fr).
     * See {@link main.utils.LanguageManager LanguageManager} for more details.
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
     * Validates whether the user is valid by running the username and password text-fields against the database table
     * for users. If the user is valid logs the user in global state for reference.
     *
     * @return Returns true if the user is able to login with the username and password field. Or returns false
     * if those fields did not match in the database.
     */
    @FXML
    private boolean userIsValid() {
        PreparedStatement ps;
        ResultSet rs;
        String query = "Select * From users";
        if (UserNameTextField.getText() != null && PassWordTextField.getText() != null) {
            // Reassign these for easier access
            String username = UserNameTextField.getText();
            String password = PassWordTextField.getText();

            try {
                ps = Connection.getConnection().prepareStatement(query);
                rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        User currentUser = new User(
                                rs.getInt("User_ID"),
                                rs.getString("User_Name"),
                                rs.getString("Password")
                        );

                        //Add all the appointments here from the script to the data model.
                        if (currentUser.getUserName().equals(username) && currentUser.getPassword().equals(password)) {
                            ObservableManager.currentlyLoggedInUser = currentUser;
                            return true;
                        }
                    }
                    //cleanup
                    rs.close();
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error accessing the users table for Log-In Validation");
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    /**
     * Removes the red borders from the Username Text Field by setting the FX:Style for the border color
     * back to the original bluish color.
     */
    @FXML
    private void removeRedBordersUsername() {
        UserNameTextField.setStyle("-fx-border-color: #97CBFF");
    }

    /**
     * Removes the red borders from the Password Text Field by setting the FX:Style for the border color
     * back to the original bluish color.
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
     * Attempts to Log in the user by firstly determining if the user is valid. If so the user is transitioned to the
     * main screen for appointments and check is made to see if the current user had any upcoming appointments in the
     * next 15 minutes. If they do an alert will be shown informing them as such.
     * <p>
     * Regardless of a successful or unsuccessful login a record of activity is kept track in login_activity.txt
     *
     * @throws IOException Signals an I/O exception has occurred. Do to trying to access files for record keeping for
     *                     login_activity.txt from the TimeManger.recordAttempt method.
     */
    @FXML
    public void loginUser() throws IOException {
        //If the username is null logs as blank user
        if (userIsValid()) {
            TimeManager.recordAttempt(UserNameTextField.getText(), true);
            StageManager.transitionNextScene("appointments");
            TimeManager.checkForUpcomingAppointment();
        } else {
            TimeManager.recordAttempt(UserNameTextField.getText(), false);
            displayErrorCodeStyling();
        }
    }

    /**
     * Hides the error notification text by setting the FX:Style opacity to 0 rendering it unseeable.
     */
    @FXML
    private void hideErrorText() {
        ErrorNotificationText.setOpacity(0);
    }

    //***************
    //Regular Methods
    //***************

    /**
     * Displays the Error Code text by setting the FX:Style opacity to 1. Clears the fields username and password.
     * Resets the mouse focus and makes the borders red for username and password text-fields. A animation is applied to
     * the login box to alert the user that an error has occurred.
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
     * Sets the text on the login-screen to the value defined in the resource bundle dictionary in relation to the
     * locale that is defined by the user's computer settings. All the text is modified except for the information
     * pertaining to the zone-ID defined in the upper left corner.
     *
     * @param dictionary A resourceBundle that contains the Language properties key value pairs.
     * @param locale     The locale representing the region that the user is currently residing.
     */
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
        RegionCode.setText("ZoneID: " + ZoneId.systemDefault());
    }

    /**
     * Makes the borders for the username and password text fields red. By modifying the FX:Style for border-color.
     */
    private void makeRedBorders() {
        UserNameTextField.setStyle("-fx-border-color: red");
        PassWordTextField.setStyle("-fx-border-color: red");
    }

    /**
     * Shakes the login box left to right simulating a wiggle. This animation is normally only shown if a login or input
     * error has occurred to let the user know something is not right.
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

    /**
     * Initializes the Login Screen called after the FXML Fields have been loaded and injected. Initializes the media
     * player for the background video and calls a method to set the text to the user's language settings.
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
        //Enable this line below to test the french language
        //Locale.setDefault(new Locale("fr"));
        //Display the Language Text
        displayLocaleLanguage();
    }
}

