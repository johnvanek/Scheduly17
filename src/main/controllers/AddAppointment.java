package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.database.Connection;
import main.utils.ObservableManager;
import main.utils.StageManager;
import main.utils.TimeManager;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.*;
import static main.utils.TimeManager.*;

public class AddAppointment implements Initializable {

    //FXML
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
        populateDataComboBoxes();
        StartTimeComboBox.setItems(StartTimesAddApp);
        //TODO might have to rework how this is set or will have to filter out the data for this
        EndTimeComboBox.setItems(EndTimesAddApp);

        //Todo Test these Values to ensure the algo is working correctly
        //Test These Values
        //Test case given an appointment in the database from 10 - 11am
        //10-1030 should overlap
        //10-11
        //1030 -11
        //9:30 to 11
        //9:30 - 10:30
        //9:30 - 11:30
        System.out.println("****************************************************");
        System.out.println("**************Testing the TimeZones*****************");

        System.out.println("Your ZoneID is " + ZoneId.systemDefault());

    }

    //FXML METHODS*********************************

    @FXML
    void submitNewAppointment(MouseEvent event) throws SQLException {
        verifyIfValidAndSubmit();
    }

    private void verifyIfValidAndSubmit() throws SQLException {
        // TODO Reformat this similar to Appointments.Java Query to account for possible null result set
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
        TimeManager.trimToEST();//If the customer is not already booked
        if (isCustomerAvailable(custID, appDate, endDateTime)) {
           //Add this appointment to the database
            String query = "INSERT INTO appointments (Title,Description,Location,Type,Start,End,Create_Date,Created_By," +
                    "Last_Update,Last_Updated_By,Customer_ID,User_ID,Contact_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = Connection.getConnection().prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, loc);
            ps.setString(4, type);
            //Have to convert this to UTC before I insert it into the database
            // The Combo boxes should be in localtime so this conversion is local time to UTC
            //With this driver should auto convert to UTC
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
            //After adding in new data clear and repopulate the appointment data on the front-end
            ObservableManager.populateDataAppointmentLists();
            //If there are no errors clean up and take us back to appointments
            ps.close();
            StageManager.setTitle("appointments");
            StageManager.setScene("appointments");
        }
    }


    @FXML
    void DisplayAppointments(ActionEvent event) {
        StageManager.setTitle("appointments");
        StageManager.setScene("appointments");
    }

    @FXML
    void DisplayCustomers(ActionEvent event) {
        System.out.println("Scene-Changing-Customers");
        StageManager.setTitle("customers");
        StageManager.setScene("customers");
    }

    @FXML
    void DisplayRecords(ActionEvent event) {


    }

    @FXML
    void TerminateSession(ActionEvent event) {

    }

}