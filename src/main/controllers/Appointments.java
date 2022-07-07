package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.DAO.models.Appointment;
import main.utils.ObservableManager;
import main.utils.StageManager;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.*;

public class Appointments implements Initializable {

    static {
        // The query for this should come from table helper
        try {
            //Maybe add another method to create tableview
            ObservableManager.createAppointmentList("SELECT * FROM appointments");
        } catch (SQLException e) {
            System.out.println("Problem Creating ObservableList's");
            throw new RuntimeException(e);
        }
    }

    //TODO implement the code here for the weekly and the monthly view
    // Test the weekly and monthly Views by creating the the add functionality.
    // Watch outback truckers

    //FXML-ID'S**************************************************************************

    //Week-Tab - ID'S
    @FXML
    private TableView<Appointment> WeekView;

    @FXML
    private TableColumn<?, ?> WeekViewAppID;

    @FXML
    private TableColumn<?, ?> WeekViewTitle;

    @FXML
    private TableColumn<?, ?> WeekViewDes;

    @FXML
    private TableColumn<?, ?> WeekViewLoc;

    @FXML
    private TableColumn<?, ?> WeekViewCon;

    @FXML
    private TableColumn<?, ?> WeekViewType;

    @FXML
    private TableColumn<?, ?> WeekViewDateStart;

    @FXML
    private TableColumn<?, ?> WeekViewDateEnd;

    @FXML
    private TableColumn<?, ?> WeekViewCustID;

    @FXML
    private TableColumn<?, ?> WeekViewUserID;

    //Month-Tab - ID'S
    @FXML
    private TableView<Appointment> MonthView;

    @FXML
    private TableColumn<?, ?> MonthViewAppID;

    @FXML
    private TableColumn<?, ?> MonthViewTitle;

    @FXML
    private TableColumn<?, ?> MonthViewDes;

    @FXML
    private TableColumn<?, ?> MonthViewLoc;

    @FXML
    private TableColumn<?, ?> MonthViewCon;

    @FXML
    private TableColumn<?, ?> MonthViewType;

    @FXML
    private TableColumn<?, ?> MonthViewDateStart;

    @FXML
    private TableColumn<?, ?> MonthViewDateEnd;

    @FXML
    private TableColumn<?, ?> MonthViewCustID;

    @FXML
    private TableColumn<?, ?> MonthViewUserID;


    //Modify-Tab - ID'S
    @FXML
    private TableView<Appointment> AllView;

    @FXML
    private TableColumn<?, ?> AllViewAppID;

    @FXML
    private TableColumn<?, ?> AllViewTitle;

    @FXML
    private TableColumn<?, ?> AllViewDes;

    @FXML
    private TableColumn<?, ?> AllViewLoc;

    @FXML
    private TableColumn<?, ?> AllViewCon;

    @FXML
    private TableColumn<?, ?> AllViewType;

    @FXML
    private TableColumn<?, ?> AllViewDateStart;

    @FXML
    private TableColumn<?, ?> AllViewDateEnd;

    @FXML
    private TableColumn<?, ?> AllViewCustID;

    @FXML
    private TableColumn<?, ?> AllViewUserID;


    //FXML-METHODS************************************

    @FXML
    void DisplayAppointments(ActionEvent event) {
        StageManager.setTitle("appointments");
        StageManager.setScene("appointments");
    }

    @FXML
    void DisplayAddAppointment(MouseEvent event) {
        System.out.println("Attempting to load add screen");
        StageManager.setTitle("addappointment");
        StageManager.setScene("addAppointment");
    }

    @FXML
    void DisplayCustomers(ActionEvent event) {
        System.out.println("Scene-Changing-Customers");
        StageManager.setTitle("customers");
        StageManager.setScene("customers");
    }

    @FXML
    void DisplayRecords(ActionEvent event) {
        System.out.println("I am displaying the records");
    }

    @FXML
    void TerminateSession(ActionEvent event) {
        System.out.println("I am closing the session");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initialize the Table Views
        System.out.println("Initializing Table View's For Appointment Screen");
        //Initialize the Month Tab
        WeekView.setItems(appointmentListWeek);
        WeekViewAppID.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        WeekViewTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        WeekViewDes.setCellValueFactory(new PropertyValueFactory<>("Description"));
        WeekViewLoc.setCellValueFactory(new PropertyValueFactory<>("Location"));
        WeekViewType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        WeekViewDateStart.setCellValueFactory(new PropertyValueFactory<>("StartDateTime"));
        WeekViewDateEnd.setCellValueFactory(new PropertyValueFactory<>("EndDateTime"));
        WeekViewCustID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        WeekViewUserID.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        WeekViewCon.setCellValueFactory(new PropertyValueFactory<>("ContactId"));
        //Initialize the Month Tab
        MonthView.setItems(appointmentListMonth);
        MonthViewAppID.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        MonthViewTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        MonthViewDes.setCellValueFactory(new PropertyValueFactory<>("Description"));
        MonthViewLoc.setCellValueFactory(new PropertyValueFactory<>("Location"));
        MonthViewType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        MonthViewDateStart.setCellValueFactory(new PropertyValueFactory<>("StartDateTime"));
        MonthViewDateEnd.setCellValueFactory(new PropertyValueFactory<>("EndDateTime"));
        MonthViewCustID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        MonthViewUserID.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        MonthViewCon.setCellValueFactory(new PropertyValueFactory<>("ContactId"));
        //Initialize all Tab
        AllView.setItems(appointmentList);
        AllViewAppID.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        AllViewTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        AllViewDes.setCellValueFactory(new PropertyValueFactory<>("Description"));
        AllViewLoc.setCellValueFactory(new PropertyValueFactory<>("Location"));
        AllViewType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        AllViewDateStart.setCellValueFactory(new PropertyValueFactory<>("StartDateTime"));
        AllViewDateEnd.setCellValueFactory(new PropertyValueFactory<>("EndDateTime"));
        AllViewCustID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        AllViewUserID.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        AllViewCon.setCellValueFactory(new PropertyValueFactory<>("ContactId"));
    }
}
