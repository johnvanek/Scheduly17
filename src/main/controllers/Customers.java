package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import main.utils.StageManager;

public class Customers {

    @FXML
    void DisplayAppointments(ActionEvent event) {
        StageManager.setTitle("appointments");
        StageManager.setScene("appointments");
    }

    @FXML
    void DisplayCustomers(ActionEvent event) {
        StageManager.setTitle("customers");
        StageManager.setScene("appointments");
    }

    @FXML
    void DisplayRecords(ActionEvent event) {

    }

    @FXML
    void TerminateSession(ActionEvent event) {

    }

    @FXML
    void DisplayAddCustomer(MouseEvent event) {
        StageManager.setTitle("add-Customer");
        StageManager.setScene("addCustomer");
    }

}
