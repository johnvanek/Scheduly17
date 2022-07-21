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

import static main.utils.ObservableManager.*;

public class Reports implements Initializable {

    @FXML
    private TextField TotalTextBox;

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


    //FXML Methods
    //Menu-Bar-Methods
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

    }

    @FXML
    void DeselectTableRow(MouseEvent event) {

    }

    @FXML
    void PopulateContactSchedule(MouseEvent event) {

    }

    @FXML
    void DetermineSelectionReturnTotal() {
        if (MonthSelectComboBox.getValue() != null && TypeSelectComboBox.getValue() != null) {
            // first get the values of both of the boxs
            Month monthSelected = MonthSelectComboBox.getValue();
            String typeSelected = TypeSelectComboBox.getValue();

            TotalTextBox.setText(CalculateMonthTypeAppointments(monthSelected,typeSelected));
        }
    }

    //Local Methods
    private String CalculateMonthTypeAppointments(Month monthSelected, String typeSelected) {
        AppointmentAllList.forEach(appointment -> {

        });
        return "Hi";
    }

    //Add method to determine what type of total to show based on the combobox selections.

    //Local Methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Init the data
        ObservableManager.generateMonthList();
        ObservableManager.generateTypeList();

        //Bind the typeList
        MonthSelectComboBox.setItems(MonthList);
        TypeSelectComboBox.setItems(TypeList);
        //Need to set listeners
        TotalTextBox.setText("5");
    }
}
