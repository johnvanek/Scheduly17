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

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UpdateAppointment implements Initializable {


    //local class data
    public static Appointment appSelected;
    //FXML-ID'S*****************************************
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
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

    @FXML
    void SubmitUpdate(MouseEvent event) {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("The value of passing data is " + appSelected.getTitle());
        prefillData();
        // TODO when initialized pre-fill out the fields with the data that has been passed from appointments.java
        //  Copy over as much code as possible from Appointments add or move some of it into a separate class.

    }

    private void prefillData() {
        //Get that is stored.
        Appointment data = appSelected;
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

    private void submitUpdate() {
        //perform update sql operation
        //only perform the update is the customer is available ignoring the current appointment
    }


}
