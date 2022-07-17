package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import main.DAO.models.Country;
import main.DAO.models.Division;
import main.database.Connection;
import main.utils.ObservableManager;
import main.utils.StageManager;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.populateDataCustomerList;

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
    void Submit(MouseEvent event) throws SQLException {
        if (isFieldsFilledOut()) {
            verifyIfValidAndSubmit();
        }
    }

    private Boolean isFieldsFilledOut() {
        //Reference variables
        String custName = CustomerNameTextField.getText();
        String custAddress = AddressTextField.getText();
        String custPostalCode = PostalCodeTextField.getText();
        String custPhone = PhoneNumberTextField.getText();

        Country countrySelected = CountryComboBox.getValue();
        Division divisionSelected = DivisionComboBox.getValue();
        //The Alert information
        Alert emptyFields = new Alert(Alert.AlertType.WARNING);
        emptyFields.setHeaderText("[WARNING] [EMPTY-FIELDS]");
        emptyFields.setContentText("Please Fill out all Fields");

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

    private void verifyIfValidAndSubmit() throws SQLException {
        String custName = CustomerNameTextField.getText();
        String custAddress = AddressTextField.getText();
        String custPostalCode = PostalCodeTextField.getText();
        String custPhone = PhoneNumberTextField.getText();
        Division divisionSelected = DivisionComboBox.getValue();

        String query = "INSERT INTO customers (" +
                "Customer_Name," +
                "Address," +
                "Postal_Code," +
                "Phone," +
                "Create_Date," +
                "Created_By," +
                "Last_Update," +
                "Last_Updated_By," +
                "Division_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = Connection.getConnection().prepareStatement(query);
        ps.setString(1, custName);
        ps.setString(2, custAddress);
        ps.setString(3, custPostalCode);
        ps.setString(4, custPhone);
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(6, "John Vanek");
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, "John Vanek");
        ps.setInt(9, divisionSelected.getDivisionId());

        ps.executeUpdate();

        ps.close();
        //Let them know that it went through
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Customer added. Redirecting you back to the " +
                "main menu.", ButtonType.OK);

        success.setHeaderText("[SUCCESSFUL] : [SUBMISSION]");
        success.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        success.showAndWait();
        StageManager.transitionNextScene("customers");
        //Repopulate the data
        populateDataCustomerList();
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
