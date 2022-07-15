package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.DAO.models.Appointment;
import main.utils.StageManager;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAppointment implements Initializable {

    //local class data
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
    private ComboBox<?> StartTimeComboBox;

    @FXML
    private ComboBox<?> EndTimeComboBox;

    @FXML
    private TextField CustIDTextField;

    @FXML
    private TextField UserIDTextField;

    @FXML
    void DisplayAppointments(ActionEvent event) {

    }

    @FXML
    void DisplayCustomers(ActionEvent event) {

    }

    @FXML
    void DisplayRecords(ActionEvent event) {

    }

    @FXML
    void SubmitUpdate(MouseEvent event) {

    }

    @FXML
    void TerminateSession(ActionEvent event) {

    }

    //FXML METHODS*******************************

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("The value of passing data is " + appSelected.getTitle());
    }
}