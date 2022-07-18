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
import main.DAO.models.Customer;
import main.DAO.models.Division;
import main.database.Connection;
import main.utils.ObservableManager;
import main.utils.StageManager;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.*;

public class UpdateCustomer implements Initializable {
    //referenceData
    public static Customer customerSelected;

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

    //LocalMethods
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
        //TODO the update This is an insert replace with add from Update appointment
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
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Customer Updated. Redirecting you back to the " +
                "main menu.", ButtonType.OK);

        success.setHeaderText("[SUCCESSFUL] : [UPDATE]");
        success.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        success.showAndWait();
        StageManager.transitionNextScene("customers");
        //Repopulate the data
        populateDataCustomerList();
    }

    private void populateDataDivisionComboBox(Country country) {
        DivisionComboBox.setItems(ObservableManager.searchByCountryCode(ObservableManager.DivisionList, country.getCountryId()));
    }


    private Division getDivisionFromDivisionID(int divisionID) {
        List<Division> division = DivisionList
                .stream()
                .filter(div -> div.getDivisionId() == divisionID).toList();
        return division.get(0);
    }

//    Country getCountryFromDivisionID(int divisionID) {
//        //get the division from DivisionID then
//        List<Division> divisionList = DivisionList
//                .stream()
//                .filter(div -> div.getDivisionId() == divisionID)
//                .toList();
//        Division divForCountryID = divisionList.get(0);
//        //Use that division to get a countryID to return a Country List
//        int countryID = divForCountryID.getCountryId();
//        List<Country> countryList = CountryList
//                .stream()
//                .filter(country -> country.getCountryId() == countryID)
//                .toList();
//        return countryList.get(0);
//    }

    private void prepopulateDivisionComboBox(Division division) {
        DivisionComboBox.setValue(division);
    }

    private void prepopulateCountryComboBox() {
        Division div = getDivisionFromDivisionID(customerSelected.getDivisionId());
        int countryID = div.getCountryId();
        List<Country> country = CountryList
                .stream()
                .filter(con -> con.getCountryId() == countryID).toList();
        CountryComboBox.setValue(country.get(0));
        populateDataDivisionComboBox(CountryComboBox.getValue());
    }

    private void prefillData() {
        CustomerNameTextField.setText(customerSelected.getCustomerName());
        AddressTextField.setText(customerSelected.getAddress());
        PostalCodeTextField.setText(customerSelected.getPostalCode());
        PhoneNumberTextField.setText(customerSelected.getPhoneNumber());
        prepopulateCountryComboBox();
        prepopulateDivisionComboBox(getDivisionFromDivisionID(customerSelected.getDivisionId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //On init pull the data
        ObservableManager.populateDataCustomerComboBoxes();
        ObservableManager.populateDataDivisionList();
        CountryComboBox.setItems(ObservableManager.CountryList);
        prefillData();
        //Take the passed data and prepopulate the fields.
    }
}
