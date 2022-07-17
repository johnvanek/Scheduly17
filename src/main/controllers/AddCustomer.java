package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.DAO.models.Country;
import main.DAO.models.Division;
import main.utils.ObservableManager;
import main.utils.StageManager;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

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

    @FXML
    void Submit(MouseEvent event) {
        // Just for testing if the second box value is changing
        System.out.println("Current Value Province ComboBox " + DivisionComboBox.getValue());
        // TODO copy and paste the method from verify and submit from one of the other appointment
        if (isFieldsFilledOut()) {
            System.out.println("performing verification of null values");
            //verifyIfValidAndSubmit();
        }
    }

    private Boolean isFieldsFilledOut() {
        //The Alert information
        Alert emptyFields = new Alert(Alert.AlertType.WARNING);
        emptyFields.setHeaderText("[WARNING] [EMPTY-FIELDS]");
        emptyFields.setContentText("Please Fill out all Fields");

        String custName = CustomerNameTextField.getText();
        String custAddress = AddressTextField.getText();
        String custPostalCode = PostalCodeTextField.getText();
        String custPhone = PhoneNumberTextField.getText();

        Country countrySelected = CountryComboBox.getValue();
        Division divisionSelected = DivisionComboBox.getValue();

        if (custName == null || custName.trim().isEmpty()) {
            emptyFields.showAndWait();
            return false;
        } else if (custAddress == null || custAddress.trim().isEmpty()) {
            emptyFields.showAndWait();
            return false;
        } else if (custPostalCode == null || custPostalCode.trim().isEmpty()) {
            emptyFields.showAndWait();
            return false;
        } else if (custPhone == null || custPhone.trim().isEmpty()) {
            emptyFields.showAndWait();
            return false;
        } else if (countrySelected == null) {
            emptyFields.showAndWait();
            return false;
        } else if (divisionSelected == null) {
            emptyFields.showAndWait();
            return false;
        }
        return true;
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
    }
}
