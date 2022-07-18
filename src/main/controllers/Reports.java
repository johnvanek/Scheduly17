package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import main.DAO.models.Customer;
import main.utils.ObservableManager;
import main.utils.StageManager;

import java.net.URL;
import java.util.ResourceBundle;

public class Reports implements Initializable {

    @FXML
    private TableView<?> ReportCustomerPerMonthTable;

    @FXML
    private TableColumn<?, ?> ReportCustomerPerMonthMonthColumn;

    @FXML
    private TableColumn<?, ?> ReportCustomerPerMonthTotalAmountColumn;

    @FXML
    private TableView<?> ReportCustomerPerTypeTable;

    @FXML
    private TableColumn<?, ?> ReportCustomerPerTypeTypeColumn;

    @FXML
    private TableColumn<?, ?> ReportCustomerPerTypeTotalAmountColumn;

    @FXML
    private TableView<?> ReportContactScheduleTable;

    @FXML
    private TableColumn<?, ?> ReportContactScheduleAppIdColumn;

    @FXML
    private TableColumn<?, ?> ReportContactScheduleTitleColumn;

    @FXML
    private TableColumn<?, ?> ReportContactScheduleTypeColumn;

    @FXML
    private TableColumn<?, ?> ReportContactScheduleDescriptionColumn;

    @FXML
    private TableColumn<?, ?> ReportContactScheduleStartDateTimeColumn;

    @FXML
    private TableColumn<?, ?> ReportContactScheduleEndDateTimeColumn;

    @FXML
    private TableColumn<?, ?> ReportContactScheduleCustomerID;

    @FXML
    private ComboBox<?> ContactSelectionComboBox;

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
    void DeselectTableRow(MouseEvent event) {

    }

    @FXML
    void PopulateContactSchedule(MouseEvent event) {

    }

    @FXML
    void TerminateSession(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Init the data
        ObservableManager.populateDataCustomerList();
        System.out.println("Reports initialized");
        System.out.println("CustomerList");
        for (Customer customer: ObservableManager.CustomerList) {
            System.out.println(customer);
            System.out.println();
        }
    }
}
