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
        //This might be the more comfortable way to code.
    }

    @FXML
    void TerminateSession(ActionEvent event) {
        //Add the code for this session later.

    }

    @FXML
    void DisplayAddCustomer(MouseEvent event) {
        StageManager.setTitle("add-Customer");
        StageManager.setScene("addCustomer");
    }

}
