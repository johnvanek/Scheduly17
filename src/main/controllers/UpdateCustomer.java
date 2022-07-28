package main.controllers;

import javafx.collections.ObservableList;
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
import main.utils.TimeManager;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.*;

/**
 * This class represents the controller logic for the view of the same name. The related view is shown
 * after navigating from the customers update button and encompasses all functionality relating to the updating of customers.
 */
public class UpdateCustomer implements Initializable {
    /**
     * The selected customer object from the customers scene.
     */
    //ClassData**************************************
    public static Customer customerSelected;

    //FXML ID'S**************************************
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

    //FXML METHODS*********************************

    /**
     * Event handler for the navigation bar [Appointment]-> View calls StageManager to handle the transition to the
     * next scene. The method will route to the Main hub for appointments.
     * See the {@link StageManager StageManager} class for more details on how the transition occurs.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void changeSceneToAppointmentMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("appointments");
    }

    /**
     * Event handler for the navigation bar [Customer]-> View calls StageManager to handle the transition to the
     * next scene. The method will route to the Main hub for Customers.
     * See the {@link StageManager StageManager} class for more details on how the transition occurs.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void changeSceneToCustomerMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("customers");
    }

    /**
     * Event handler for the navigation bar [Reports]-> View calls StageManager to handle the transition to the
     * next scene. The method will route to the Main hub for Reports.
     * See the {@link StageManager StageManager} class for more details on how the transition occurs.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void changeSceneToReports(ActionEvent event) {
        StageManager.transitionNextScene("reports");
    }

    /**
     * Event handler for the navigation bar [Signout]-> Quit loses the current Connection and exits the via the System.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void terminateSession(ActionEvent event) {
        System.out.println("Terminating the application");
        Connection.closeConnection();
        System.exit(0);
    }

    /**
     * An event handler that based on the value from the Country-ComboBox assigns the FirstLevel Division Combo-Box
     * Values. See {@link #populateDataDivisionComboBox(Country) populateDataDivisionComboBox} for how that is evaluated.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void SetValueForDivisionBasedOnCountry(ActionEvent event) {
        if (CountryComboBox.getValue() != null) {
            System.out.println("A Selection had been made in Country Combo-Box");
            populateDataDivisionComboBox(CountryComboBox.getValue());
        }
    }

    /**
     * Event handler for the AddCustomer Submit button in order to submit the fields must all be NOT NULL.
     * The validation and actual database operations are handled by {@link #verifyIfValidAndSubmit}.
     *
     * @param event Represent a MouseEvent event.
     * @throws SQLException An Exception that provides information on a database error mostly relating to incorrect
     *                      access.
     */
    @FXML
    void Submit(MouseEvent event) throws SQLException {
        if (isFieldsFilledOut()) {
            verifyIfValidAndSubmit();
        }
    }

    //LocalMethods*****************************************

    /**
     * A local utility method that determines whether the fields have been filled out.
     *
     * @return Returns a boolean representing whether the fields on the form are filled out.
     */
    Boolean isFieldsFilledOut() {
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

    /**
     * Using the data from the fields on the form this method verifies if the customer is valid to add. Database insert
     * operations are
     * performed and alert is generated for the user to let them know whether the submission was a success or failure.
     * And then the data pertaining to Customers on the Front-End is regenerated.
     *
     * @throws SQLException A SqlException representing a database access error or other error has occurred.
     */
    void verifyIfValidAndSubmit() throws SQLException {
        String custName = CustomerNameTextField.getText();
        String custAddress = AddressTextField.getText();
        String custPostalCode = PostalCodeTextField.getText();
        String custPhone = PhoneNumberTextField.getText();
        Division divisionSelected = DivisionComboBox.getValue();

        String query = "UPDATE customers SET " +
                "Customer_Name = ?, " +
                "Address = ?, " +
                "Postal_Code =?, " +
                "Phone =?, " +
                "Create_Date =?, " +
                "Created_By =?, " +
                "Last_Update =?, " +
                "Last_Updated_By =?, " +
                "Division_ID =?" +
                " WHERE Customer_ID =?";

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
        ps.setInt(10, customerSelected.getCustomerId());
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

    /**
     * Populates the list of items to be shown in the Divisions combo-box based on the selected Country from the Country
     * combo-box. The logic behind the filtering is handled in
     * {@link ObservableManager#searchByCountryCode(ObservableList, int) searchByCountryCode}.
     *
     * @param country Represents a Country selected from the Country combo-box.
     */
    void populateDataDivisionComboBox(Country country) {
        DivisionComboBox.setItems(ObservableManager.searchByCountryCode(ObservableManager.DivisionList, country.getCountryId()));
    }

    /**
     * <p>
     * Given an integer representing the unique number for each division the data for divisions is filtered over and
     * a Division is returned that matches the predicate. If there is no match a null Division is returned.
     * </p>
     *
     * <p><b>Lambda [2]</b> - Using {@link ObservableManager#DivisionList DivisionList} I converted the List to a stream()
     * then used the filter method to drill down. To match the predicate the Division Id of the list item has to match
     * the given int, which then is put into a list containing just one list item so that is can be returned. </p>
     *
     * @param divisionID An integer representing the unique ID of a division.
     * @return A Division data model object representing provinces and states see {@link Division} for more information.
     */
    Division getDivisionFromDivisionID(int divisionID) {
        List<Division> division = DivisionList
                .stream()
                .filter(div -> div.getDivisionId() == divisionID).toList();
        return division.get(0);
    }

    /**
     * Pre-populates the Division ComboBox based on the Division that is passed as a parameter.
     * @param division A Division data model object representing provinces and states see {@link Division} for more information.
     */
    void prepopulateDivisionComboBox(Division division) {
        DivisionComboBox.setValue(division);
    }

    /**
     * <p>
     * Given an integer representing the unique number for each Country the data for Countries is filtered over and
     * a Country is returned that matches the predicate and is assigned as the value for the combo-box.
     * </p>
     *
     * <p><b>Lambda [3]</b> - Using {@link ObservableManager#CountryList CountryList} I converted the List to a stream()
     * then used the filter method to drill down. To match the predicate the Country Id of the list item has to match
     * the given int, which then is put into a list containing just one list item so that is can be returned.
     * Almost exactly the same as <b>Lambda [2]</b> I don't consider it unique.
     * </p>
     */
    void prepopulateCountryComboBox() {
        Division div = getDivisionFromDivisionID(customerSelected.getDivisionId());
        int countryID = div.getCountryId();
        List<Country> country = CountryList
                .stream()
                .filter(con -> con.getCountryId() == countryID).toList();
        CountryComboBox.setValue(country.get(0));
        populateDataDivisionComboBox(CountryComboBox.getValue());
    }

    /**
     * Using the data from the selected Customer the form data is pre-populated.
     */
    void prefillData() {
        CustomerNameTextField.setText(customerSelected.getCustomerName());
        AddressTextField.setText(customerSelected.getAddress());
        PostalCodeTextField.setText(customerSelected.getPostalCode());
        PhoneNumberTextField.setText(customerSelected.getPhoneNumber());
        prepopulateCountryComboBox();
        prepopulateDivisionComboBox(getDivisionFromDivisionID(customerSelected.getDivisionId()));
    }

    /**
     * Initializes the UpdateCustomer scene called after the FXML Fields have been loaded and injected. Initializes and
     * populates the data for the ComboBoxes. The ComboBoxes are representations of data from the
     * observable lists representing Countries and Divisions in {@link ObservableManager}.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //On init pull the data
        ObservableManager.populateDataCustomerComboBoxes();
        ObservableManager.populateDataDivisionList();
        CountryComboBox.setItems(ObservableManager.CountryList);
        //Prefill the passed data
        prefillData();
    }

    private UpdateCustomer() {
    }
}
