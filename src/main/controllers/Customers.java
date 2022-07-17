package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.DAO.models.Customer;
import main.utils.ObservableManager;
import main.utils.StageManager;

import java.net.URL;
import java.util.ResourceBundle;

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
        //Create the Data-List
        ObservableManager.populateDataCustomerList();
        ObservableManager.populateDataCustomerComboBoxes();
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
