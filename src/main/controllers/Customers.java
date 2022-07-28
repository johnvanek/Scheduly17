package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import main.DAO.models.Customer;
import main.database.Connection;
import main.utils.ObservableManager;
import main.utils.StageManager;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.populateDataAppointmentLists;
import static main.utils.ObservableManager.populateDataCustomerList;

/**
 * This Class represents the controller logic for the view of the same name. The related view is shown
 * after navigating from the navbar and encompasses all functionality relating to Customers.
 */
public class Customers implements Initializable {
    private Customers() {
    }

    //FXML ID's
    @FXML
    private TableView<Customer> CustomersTableView;

    @FXML
    private TableColumn<?, ?> CustomerID;

    @FXML
    private TableColumn<?, ?> CustomerName;

    @FXML
    private TableColumn<?, ?> CustomerAddress;

    @FXML
    private TableColumn<?, ?> CustomerPostalCode;

    @FXML
    private TableColumn<?, ?> CustomerPhoneNumber;


    @FXML
    private TableColumn<?, ?> CustomerCountryCode;

    //FXML METHODS
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
     * Event handler for the Add button in the modifiers section changes the scene to the AddCustomer scene.
     * StageManager handles the transition.
     *
     * @param event Represents a mouse event.
     */
    @FXML
    void changeSceneAddCustomer(MouseEvent event) {
        StageManager.transitionNextScene("addCustomer");
    }

    /**
     * Event handler for the update button in the modifiers section changes the scene to the Update Customer scene.
     * Will only update if the selection from Customers is valid StageManager will handle the transition.
     *
     * @param event Represents a mouse event.
     */
    @FXML
    void changeSceneUpdateCustomer(MouseEvent event) {
        if (CustomersTableView.getSelectionModel().getSelectedItem() != null) {
            Customer selection = CustomersTableView.getSelectionModel().getSelectedItem();
            StageManager.transitionNextScene("updateCustomer", selection);
        }
    }

    /**
     * <p>
     * Event handler for the delete button in the modifers section. If there is a selected table row an attempt is made
     * to delete a selected Customer via JDBC if the user agrees to continue. If successful
     * all the selected customer's appointments will also be deleted from the database,
     * whether successful or not the front-end data models get recreated as a result of potentially altering the records.
     * </p>
     * <p>
     * <b>Lambda [1]</b> - Using the Alert class with the method .showAndWait() and
     * chaining that method with .isPresent() [a consumer] allows me to pass code to evaluate if the
     * evaluates to true.
     *
     * The user is presented the Alert with the warning that performing a delete is a permanent action, if the boolean
     * for the response evaluates true the code to perform a deletion executes allowing me to handle the event right
     * there inline anonymously.
     * </p>
     *
     * @param event Represents a mouse event.
     */
    @FXML
    void deleteCustomer(MouseEvent event) {
        //The Alert Code
        Alert confirmDelete = new Alert(Alert.AlertType.WARNING);
        confirmDelete.setHeaderText("[WARNING] : [DELETION]");
        confirmDelete.getButtonTypes().add(ButtonType.CANCEL);
        confirmDelete.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Customer selectedCustomer = null;
        //If there is a current selection in the all view
        if (CustomersTableView.getSelectionModel().getSelectedItem() != null) {
            selectedCustomer = CustomersTableView.getSelectionModel().getSelectedItem();
            confirmDelete.setContentText("[Warning!] - You are about to delete Customer[ID]: " +
                    selectedCustomer.getCustomerId() + " . " +
                    "Named " + selectedCustomer.getCustomerName() + " " +
                    "[WARNING] - This will also delete all of the customers scheduled " +
                    "appointments are you sure you wish to continue? " +
                    "This action cannot be reversed!");
            Customer effectivelyFinalSelectedCustomer = selectedCustomer;
            confirmDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String query = "DELETE FROM appointments WHERE Customer_ID = " +
                            effectivelyFinalSelectedCustomer.getCustomerId();

                    String query2 = "DELETE FROM customers WHERE Customer_ID = " +
                            effectivelyFinalSelectedCustomer.getCustomerId();

                    try {
                        //Try the first query
                        PreparedStatement ps = Connection.getConnection().prepareStatement(query);
                        System.out.println(ps.executeUpdate() + " database records have been deleted. From appointments.");
                        //Attempt the second by reassigning the prepared statement's query
                        ps = Connection.getConnection().prepareStatement(query2);
                        System.out.println(ps.executeUpdate() + " database records have been deleted. From customers.");
                        //Cleanup
                        ps.close();

                        //When deletion does happen repopulate the Front end data.
                        populateDataAppointmentLists();
                        populateDataCustomerList();
                    } catch (SQLException e) {
                        System.out.println("There was a problem trying to perform a deletion Customers Page");
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    /**
     * Initializes the Customers scene called after the FXML Fields have been loaded and injected. Initializes and
     * populates the Front end data relating to appointments. The table view are representation of data is from the
     * Customers observable list in {@link ObservableManager}.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Create the Data-List
        ObservableManager.populateDataCustomerList();
        // Bind the Data
        CustomersTableView.setItems(ObservableManager.CustomerList);
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        CustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        CustomerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        CustomerCountryCode.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }
}
