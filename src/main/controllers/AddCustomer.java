package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.DAO.models.Country;
import main.DAO.models.Division;
import main.utils.ObservableManager;
import main.utils.StageManager;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddCustomer implements Initializable {
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

    @FXML
    void SetValueForDivisionBasedOnCountry(ActionEvent event) {
        if (CountryComboBox.getValue() != null) {
            System.out.println("A Selection had been made in Country Combo-Box");
            populateDataDivisionComboBox(CountryComboBox.getValue());
        }

    }


    private void populateDataDivisionComboBox(Country country) {
        DivisionComboBox.setItems(ObservableManager.searchByCountryCode(ObservableManager.DivisionList, country.getCountryId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Add the data
        ObservableManager.populateDataCustomerComboBoxes();
        ObservableManager.populateDataDivisionList();
        CountryComboBox.setItems(ObservableManager.CountryList);


        //TODO add an event listener to the country comboBox

    }
}
