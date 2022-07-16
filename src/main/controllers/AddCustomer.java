package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.utils.StageManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO The combo box for countries in add needs to be backed in an observable List
        //  Then it needs to be set here in initialized
        //  The more difficult task is populating the provinces and states comboBox
        //  the second combo box has to be disabled and only enabled after a country is picked in which case it then
        //  Shows the correct observable list.
        //  Probably going to need an observable list for each country, or some way to filter once list of counties
        //  And return a new list
    }

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
