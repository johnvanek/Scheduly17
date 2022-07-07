package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.DAO.models.Appointment;
import main.utils.StageManager;
import main.utils.TimeManager;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.*;

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
    private DatePicker EndDatePicker;
    @FXML
    private ComboBox<LocalTime> EndTimeComboBox;
    @FXML
    private TextField CustIDTextField;
    @FXML
    private TextField UserIDTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize the combo-box values
        createAddAppointmentData();
        StartTimeComboBox.setItems(addAppointmentStartTimes);
        EndTimeComboBox.setItems(addAppointmentEndTimes);
    }

    //FXML METHODS*********************************

    @FXML
    void SubmitNewAppointment(MouseEvent event) {
        getDataFromFields();
    }

    private Appointment getDataFromFields() {
        String title = TitleTextField.getText();
        String desc = DescTextField.getText();
        String loc = LocTextField.getText();
        int con = Integer.parseInt(ConTextField.getText());
        String type = TypeTextField.getText();
        LocalDate startDate = StartDatePicker.getValue();
        LocalTime startTime = StartTimeComboBox.getValue();
        LocalDate endDate = EndDatePicker.getValue();
        LocalTime endTime = EndTimeComboBox.getValue();
        int custID = Integer.parseInt(CustIDTextField.getText());
        int userID = Integer.parseInt(UserIDTextField.getText());

        //TODO
        // perform the validation



        LocalDateTime startDateTime = startDate.atTime(startTime);
        System.out.println("The start date and time is " + startDateTime);
        LocalDateTime endDateTime = endDate.atTime(endTime);
        System.out.println("The end date and time is " + endDateTime);
        //If it is in the business hours and there is no overlap go ahead and add it the database.
        if(TimeManager.isInBusinessHours(startDateTime,endDateTime) && TimeManager.isCustomerAvailable(custID,startDateTime,endDateTime)){
            //If they pass these validation we are good to call an operation inside of here to add it to the database.
        }

        return null;
        //return new Appointment(title,desc,loc,con,type)
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
        //TODO add screen change here to records
    }

    @FXML
    void TerminateSession(ActionEvent event) {
        //TODO add screen change here to quit the application
    }

}