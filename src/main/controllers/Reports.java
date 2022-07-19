package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.DAO.models.Customer;
import main.DAO.models.MonthReport;
import main.utils.ObservableManager;
import main.utils.StageManager;

import java.net.URL;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.AppointmentWeeklyList;

public class Reports implements Initializable {

    @FXML
    private TableView<MonthReport> ReportCustomerPerMonthTable;

    @FXML
    private TableColumn<?, ?> ReportCustomerPerMonthMonthColumn;

    @FXML
    private TableColumn<?, ?> ReportCustomerPerMonthTotalAmountColumn;

    @FXML
    private ComboBox<Customer> CustomerReportMonthComboBox;

    @FXML
    private TableView<?> ReportCustomerPerTypeTable;

    @FXML
    private TableColumn<?, ?> ReportCustomerPerTypeTypeColumn;

    @FXML
    private TableColumn<?, ?> ReportCustomerPerTypeTotalAmountColumn;

    @FXML
    private ComboBox<Customer> CustomerReportTypeComboBox;

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

    //Local Methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Init the data
        System.out.println("Reports initialized");
        ObservableManager.generateMonthReport();
        ReportCustomerPerMonthTable.setItems(ObservableManager.MonthReportList);
        ReportCustomerPerMonthMonthColumn.setCellValueFactory(new PropertyValueFactory<>("Month"));
        ReportCustomerPerMonthTotalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentCount"));
    }
}
