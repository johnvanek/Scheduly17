package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import main.database.Connection;
import main.utils.ObservableManager;
import main.utils.StageManager;
import main.utils.TimeManager;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.*;


public class AddAppointment implements Initializable {
    //FXML ID's*************************************
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize the combo-box data
        //This is probably the problem initialize gets called everytime the screen comes in
        //Seems to only be a problem for the endTime Combobox is not getting cleared
        ObservableManager.populateDataAppointmentComboBoxes();
        TimeManager.generateValidBusinessHoursList();
        StartTimeComboBox.setItems(StartTimesAddAppEst);
        EndTimeComboBox.setItems(EndTimesAddAppEst);

        System.out.println("****************************************************");
        System.out.println("**************Testing the TimeZones*****************");

        System.out.println("Printing Out Observable List's");
        System.out.println("The ones in the comboBox are StartTimes/EndTimes EST");
        System.out.println("StartTimesAll      : " + StartTimesAddApp);
        System.out.println("StartTimesFiltered : " + StartTimesFiltered);
        System.out.println("StartTimesEST      : " + StartTimesAddAppEst);
        System.out.println();
        System.out.println("EndTimesAll      : " + EndTimesAddApp);
        System.out.println("EndTimesFiltered : " + EndTimesFiltered);
        System.out.println("EndTimesEST      : " + EndTimesAddAppEst);

        System.out.println("****************************************************");
        System.out.println("****************************************************");
    }

    //FXML METHODS*********************************

    @FXML
    void submitNewAppointment(MouseEvent event) throws SQLException {
        if (isFieldsFilledOut()) {
            verifyIfValidAndSubmit();
        }
    }

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


        if (TimeManager.isCustomerAvailable(custID, appDate, endDateTime)) {
            String query = "INSERT INTO appointments (Title,Description,Location,Type,Start,End,Create_Date,Created_By," +
                    "Last_Update,Last_Updated_By,Customer_ID,User_ID,Contact_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.executeUpdate();

            ps.close();
            //Let them know that it went through
            Alert success = new Alert(Alert.AlertType.INFORMATION, "Appointment added. Redirecting you back to the " +
                    "main menu.", ButtonType.OK);

            success.setHeaderText("[SUCCESSFUL] : [SUBMISSION]");
            success.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            success.showAndWait();
            StageManager.transitionNextScene("appointments");
            //Repopulate the data
            populateDataAppointmentLists();
        }
    }

    private Boolean isFieldsFilledOut() {
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

    //FXML METHODS*******************************
    @FXML
    void ChangeSceneToAppointmentMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("appointments");
    }

    @FXML
    void ChangeSceneToCustomerMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("customers");
    }

    @FXML
    void ChangeSceneToRecords(ActionEvent event) {
        System.out.println("I am displaying the records");
    }

    @FXML
    void TerminateSession(ActionEvent event) {
        System.out.println("I am closing the session");
    }

}