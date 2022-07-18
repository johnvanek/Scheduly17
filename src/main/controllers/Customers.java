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

public class Customers implements Initializable {
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
    @FXML
    void ChangeSceneToAppointmentMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("appointments");
    }

    @FXML
    void ChangeSceneToCustomerMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("customers");
    }

    @FXML
    void ChangeSceneToReports(ActionEvent event) {
        StageManager.transitionNextScene("reports");
    }

    @FXML
    void TerminateSession(ActionEvent event) {
        System.out.println("I am closing the session");
    }

    @FXML
    void ChangeSceneAddCustomer(MouseEvent event) {
        StageManager.transitionNextScene("addCustomer");
    }

    @FXML
    void ChangeSceneUpdateCustomer(MouseEvent event) {
        if (CustomersTableView.getSelectionModel().getSelectedItem() != null) {
            Customer selection = CustomersTableView.getSelectionModel().getSelectedItem();
            StageManager.transitionNextScene("updateCustomer", selection);
        }
    }

    @FXML
    void DeleteCustomer(MouseEvent event) {
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
            //TODO document this lambda
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
