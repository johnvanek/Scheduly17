package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import main.DAO.models.Appointment;
import main.database.Connection;
import main.utils.ObservableManager;
import main.utils.StageManager;
import main.utils.TimeManager;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.*;

/**
 * This Class represents the controller logic for the view of the same name. The Class is responsible for the logic
 * encompassing the update functionality for appointments. The view is accessed via the Appointments
 * view.
 */
public class UpdateAppointment implements Initializable {

    /**
     * Represents an Appointment that was selected before the update button was clicked.
     */
    public static Appointment appSelected;
    //FXML-ID'S*****************************************
    @FXML
    private TextField TitleTextField;

    @FXML
    private TextField DescTextField;

    @FXML
    private TextField LocTextField;

    @FXML
    private TextField ConTextField;

    @FXML
    private Button SubmitNewButton;

    @FXML
    private TextField TypeTextField;

    @FXML
    private DatePicker StartDatePicker;

    @FXML
    private ComboBox<LocalTime> StartTimeComboBox;

    @FXML
    private ComboBox<LocalTime> EndTimeComboBox;

    @FXML
    private TextField CustIDTextField;

    @FXML
    private TextField UserIDTextField;

    //FXML METHODS*******************************

    /**
     * Public constructor for updateAppointment only public so that javafx can instantiate it.
     */
    public UpdateAppointment() {
    }

    /**
     * Event handler for the navigation bar [Appointment]-> View calls StageManager to handle the transition to the
     * next scene. The method will route to the Main hub for appointments.
     * See the {@link StageManager StageManager} class for more details on how the transition occurs.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void changeSceneToAppointmentMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("appointments");
    }

    /**
     * Event handler for the navigation bar [Customer]-> View calls StageManager to handle the transition to the
     * next scene. The method will route to the Main hub for Customers.
     * See the {@link StageManager StageManager} class for more details on how the transition occurs.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void changeSceneToCustomerMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("customers");
    }

    /**
     * Event handler for the navigation bar [Reports]-> View calls StageManager to handle the transition to the
     * next scene. The method will route to the Main hub for Reports.
     * See the {@link StageManager StageManager} class for more details on how the transition occurs.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void changeSceneToReports(ActionEvent event) {
        StageManager.transitionNextScene("reports");
    }

    /**
     * Event handler for the navigation bar [Signout]-> Quit loses the current Connection and exits the via the System.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void terminateSession(ActionEvent event) {
        System.out.println("Terminating the application");
        Connection.closeConnection();
        System.exit(0);
    }

    /**
     * Using the data from the selected appointment the form data is pre-populated.
     */
    void prefillData() {
        //Get that is stored.
        //Get a reference to all the fields
        // And assign the values
        TitleTextField.setText(appSelected.getTitle());
        DescTextField.setText(appSelected.getDescription());
        LocTextField.setText(appSelected.getLocation());
        TypeTextField.setText(appSelected.getType());
        //This might need to be fixed uncertain how it converts time-wise
        StartDatePicker.setValue(LocalDate.from(appSelected.getStartDateTime()));
        StartTimeComboBox.setValue(LocalTime.from(appSelected.getStartDateTime()));
        EndTimeComboBox.setValue(LocalTime.from(appSelected.getEndDateTime()));

        CustIDTextField.setText(String.valueOf(appSelected.getCustomerId()));
        UserIDTextField.setText(String.valueOf(appSelected.getUserId()));
        ConTextField.setText(String.valueOf(appSelected.getContactId()));
        //And assign these w/e there is a change from the fields in case they immediately resubmit
    }

    /**
     * A local utility method that determines whether the fields have been filled out.
     *
     * @return Returns a boolean representing whether the fields on the form are filled out.
     */
    Boolean isFieldsFilledOut() {
        //The Alert information
        Alert emptyFields = new Alert(Alert.AlertType.WARNING);
        emptyFields.setHeaderText("[WARNING] [EMPTY-FIELDS]");
        emptyFields.setContentText("Please Fill out all Fields");

        String title = TitleTextField.getText();
        String desc = DescTextField.getText();
        String loc = LocTextField.getText();
        String type = TypeTextField.getText();

        LocalDate startDate = StartDatePicker.getValue();
        LocalTime startTime = StartTimeComboBox.getValue();
        LocalTime endTime = EndTimeComboBox.getValue();

        if (title == null || title.trim().isEmpty()) {
            emptyFields.showAndWait();
            return false;
        } else if (desc == null || desc.trim().isEmpty()) {
            emptyFields.showAndWait();
            return false;
        } else if (loc == null || loc.trim().isEmpty()) {
            emptyFields.showAndWait();
            return false;
        } else if (type == null || type.trim().isEmpty()) {
            emptyFields.showAndWait();
            return false;
        } else if (startDate == null) {
            emptyFields.showAndWait();
            return false;
        } else if (startTime == null) {
            emptyFields.showAndWait();
            return false;
            //This would mean it's not initialized
        } else if (endTime == null) {
            emptyFields.showAndWait();
            return false;
        } else if ((UserIDTextField.getText() == null) || UserIDTextField.getText().trim().isEmpty() || Objects.equals(UserIDTextField.getText(), "")) {
            emptyFields.showAndWait();
            return false;
        } else if (CustIDTextField.getText() == null || CustIDTextField.getText().trim().isEmpty() || Objects.equals(CustIDTextField.getText(), "")) {
            emptyFields.showAndWait();
            return false;
        } else if (ConTextField.getText() == null || ConTextField.getText().trim().isEmpty() || Objects.equals(ConTextField.getText(), "")) {
            emptyFields.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Event handler for the UpdateAppointment Submit button in order to submit the fields must all be NOT NULL.
     * The validation and actual database operations are handled by {@link #verifyIfValidAndSubmit}.
     *
     * @param event Represents a MouseEvent event.
     * @throws SQLException An exception that provides information about a database access error or other SQL related
     *                      error.
     */
    @FXML
    private void SubmitUpdate(MouseEvent event) throws SQLException {
        if (isFieldsFilledOut()) {
            verifyIfValidAndSubmit();
        }
        AppointmentWeeklyList.clear();
        AppointmentMonthlyList.clear();
        populateDataAppointmentLists();
    }

    /**
     * Using the data from the fields on the form this method verifies if the appointment is valid to Update. The criteria
     * for whether appointment is valid or not is handled by the method {@link TimeManager#isCustomerAvailable(int,
     * LocalDateTime, LocalDateTime) isCustomerAvailable}. If the criteria is met database insert operations are
     * performed, an alert is generated for the user to let them know whether the Update was a success or failure.
     * And then the data pertaining to appointments on the Front-End is regenerated.
     *
     * @throws SQLException A SqlException representing a database access error or other error has occurred.
     */
    private void verifyIfValidAndSubmit() throws SQLException {
        String title = TitleTextField.getText();
        String desc = DescTextField.getText();
        String loc = LocTextField.getText();
        String type = TypeTextField.getText();

        LocalDate startDate = StartDatePicker.getValue();
        LocalTime startTime = StartTimeComboBox.getValue();
        LocalTime endTime = EndTimeComboBox.getValue();

        int custID = Integer.parseInt(CustIDTextField.getText());
        int userID = Integer.parseInt(UserIDTextField.getText());
        int con = Integer.parseInt(ConTextField.getText());
        //assemble the LocalDateTimeObjects
        LocalDateTime appDate = startDate.atTime(startTime);
        LocalDateTime endDateTime = startDate.atTime(endTime);

        System.out.println("Attempting to determine if valid");
        if (TimeManager.isCustomerAvailableForUpdate(custID, appDate, endDateTime, title, desc, appSelected.getAppointmentId())) {
            String query = "UPDATE appointments SET Title = ?, Description =?, Location =?, Type =?, Start =?, End =?, " +
                    "Create_Date =?, Created_By =?, Last_Update =?, Last_Updated_By =?, Customer_ID =?, User_ID =?, " +
                    "Contact_ID =? WHERE Appointment_ID =?";
            PreparedStatement ps = Connection.getConnection().prepareStatement(query);

            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, loc);
            ps.setString(4, type);

            ps.setTimestamp(5, Timestamp.valueOf(appDate));
            ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, "John Vanek");
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(10, "John Vanek");
            ps.setInt(11, custID);
            ps.setInt(12, userID);
            ps.setInt(13, con);
            ps.setInt(14, appSelected.getAppointmentId());
            ps.executeUpdate();

            ps.close();
            //Let them know that it went through
            Alert success = new Alert(Alert.AlertType.INFORMATION, "Appointment Updated. Redirecting you back to the " +
                    "main menu.", ButtonType.OK);

            success.setHeaderText("[SUCCESSFUL] : [SUBMISSION]");
            success.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            success.showAndWait();
            StageManager.transitionNextScene("appointments");
        }
    }

    /**
     * Initializes the UpdateAppointments scene called after the FXML Fields have been loaded and injected. Initializes and
     * populates the data for the ComboBoxes. The ComboBoxes are representations of data from the
     * observable lists representing time in {@link ObservableManager}.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("The value of passing data is " + appSelected.getTitle());
        ObservableManager.populateDataAppointmentComboBoxes();
        TimeManager.generateValidBusinessHoursList();
        StartTimeComboBox.setItems(StartTimesAddAppEst);
        EndTimeComboBox.setItems(EndTimesAddAppEst);
        prefillData();
    }
}
