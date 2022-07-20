package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import main.utils.ObservableManager;
import main.utils.StageManager;

import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.MonthList;
import static main.utils.ObservableManager.TypeList;

public class Reports implements Initializable {

    @FXML
    private TableView<?> ReportMonthTypeView;

    @FXML
    private TableColumn<?, ?> TotalColumnMonthType;

    @FXML
    private ComboBox<Month> MonthSelectComboBox;

    @FXML
    private ComboBox<String> TypeSelectComboBox;

    @FXML
    private TableView<?> ReportContactScheduleView;

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

        ObservableManager.generateMonthList();
        ObservableManager.generateTypeList();

        //Bind the typeList
        MonthSelectComboBox.setItems(MonthList);
        TypeSelectComboBox.setItems(TypeList);
    }
}
