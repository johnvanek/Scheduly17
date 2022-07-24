package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.DAO.models.Appointment;
import main.DAO.models.Contact;
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
    private TableView<Appointment> ReportContactScheduleView;

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
    private ComboBox<Contact> ContactSelectionComboBox;


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

            TotalTextBox.setText(CalculateMonthTypeAppointments(monthSelected, typeSelected));

            if (Integer.parseInt(TotalTextBox.getText()) > 0) {
                TotalTextBox.setStyle("-fx-text-fill: #24a6f4");
            } else TotalTextBox.setStyle("-fx-text-fill: black");

        }
    }

    @FXML
    void ShowSchedule(ActionEvent event) {
        if(ContactSelectionComboBox.getValue() != null)  {
            ObservableManager.generateContactAppointmentList(ContactSelectionComboBox.getValue());
            ShowContactSchedule();
        }
    }

    //Local Methods
    private String CalculateMonthTypeAppointments(Month monthSelected, String typeSelected) {
        int count = 0;
        //TODO document this lambda the use of count.
        if (typeSelected.equals("All Types!")) {
            count = (int) AppointmentAllList.stream().filter(appointment ->
                    appointment.getStartDateTime().getMonth().equals(monthSelected)).count();
        } else {
            count = (int) AppointmentAllList.stream().filter(appointment ->
                    appointment.getStartDateTime().getMonth().equals(monthSelected)
                            && appointment.getType().equals(typeSelected)).count();
        }

        return String.valueOf(count);
    }
    //Local Methods

    private void ShowContactSchedule(){
        ReportContactScheduleView.setItems(ContactAppointmentList);
        ReportContactScheduleAppIdColumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        ReportContactScheduleTitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        ReportContactScheduleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        ReportContactScheduleDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        ReportContactScheduleStartDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("StartDateTime"));
        ReportContactScheduleEndDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("EndDateTime"));
        ReportContactScheduleCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Init the data for the First Tab
        ObservableManager.generateMonthList();
        ObservableManager.generateTypeList();

        //Bind the data for the First Tab
        MonthSelectComboBox.setItems(MonthList);
        TypeSelectComboBox.setItems(TypeList);
        //Placeholder text First Tab
        TotalTextBox.setText("Total_AMT");

        //Init the data for the Second Tab
        ObservableManager.generateContactListFromDatabase();

        //Bind the data for the Second Tab
        ContactSelectionComboBox.setItems(ContactList);
    }
}
