package main.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import main.utils.StageManager;

import java.net.URL;
import java.util.ResourceBundle;

public class Appointments implements Initializable {

    //TODO either implement an observable list here inline which is not ideal for code reuse.
    // Or implement an observable-list manager that can be accessed as if from a class.
    // Could make a class called observerManager which managers and passes around the observable list.
    // This is probably more ideal.
    // Going to need to do this anyway as the observable list are needed just to populate the list views for the drop downs.


    // The reference for the AllView
    @FXML          
    private TableView<?> ALLVIEW;

    //Create Observable List this is an observable list of appointments.
    //Aka it is a collection of appointments.
    ObservableList<Appointments> appointmentsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Screen initialized");
    }

    @FXML
    void DisplayAppointments(ActionEvent event) {
        StageManager.setTitle("appointments");
        StageManager.setScene("appointments");
    }

    @FXML
    void DisplayAddAppointment(MouseEvent event) {
        System.out.println("Attempting to load add screen");
        StageManager.setTitle("addappointment");
        StageManager.setScene("addAppointment");
    }

    @FXML
    void DisplayCustomers(ActionEvent event) {
        System.out.println("Scene-Changing-Customers");
        StageManager.setTitle("customers");
        StageManager.setScene("customers");
    }

    @FXML
    void DisplayRecords(ActionEvent event) {
        System.out.println("I am displaying the records");
    }

    @FXML
    void TerminateSession(ActionEvent event) {
        System.out.println("I am closing the session");
    }
}
