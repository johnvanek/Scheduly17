package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.DAO.models.Country;
import main.DAO.models.Division;
import main.utils.StageManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //FXML ID'S
    @FXML
    private TextField CustomerNameTextField;

    @FXML
    private TextField AddressTextField;

    @FXML
    private TextField PostalCodeTextField;

    @FXML
    private TextField PhoneNumberTextField;

    //TODO make the data-models for Country and Divisions
    @FXML
    private ComboBox<Country> CountryComboBox;

    @FXML
    private ComboBox<Division> DivisionComboBox;




    //FXML METHODS
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
