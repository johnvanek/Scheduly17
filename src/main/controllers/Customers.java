package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import main.utils.StageManager;

import java.net.URL;
import java.util.ResourceBundle;

public class Customers implements Initializable {
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

    @FXML
    void ChangeSceneAddCustomer(MouseEvent event) {
        StageManager.transitionNextScene("addCustomer");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
