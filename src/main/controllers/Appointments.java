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

import static main.utils.ObservableManager.appointmentList;

public class Appointments implements Initializable {

    static {
        // The query for this should come from table helper
        try {
            //Maybe add another method to create tableview
            ObservableManager.CreateAppointmentList("SELECT * FROM appointments");
        } catch (SQLException e) {
            System.out.println("Problem Creating Observable");
            throw new RuntimeException(e);
        }
    }

    //TODO either implement an observable list here inline which is not ideal for code reuse.
    // Or implement an observable-list manager that can be accessed as if from a class.
    // Will have to do this for each add also could maybe use lambda.

    //FXMLIS'S**************************************************************************
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
    private TableColumn<?, ?> AllViewEndDate;

    @FXML
    private TableColumn<?, ?> AllViewCustID;

    @FXML
    private TableColumn<?, ?> AllViewUserID;


    //FXMLMETHODS************************************

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
        //Since im am going to have to do this for 3 tabs might make sense make a display manager
        System.out.println("Initializing Table View");
        //Initialize all TableView
        AllView.setItems(appointmentList);
        AllViewAppID.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        AllViewTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        AllViewDes.setCellValueFactory(new PropertyValueFactory<>("Description"));
        AllViewLoc.setCellValueFactory(new PropertyValueFactory<>("Location"));
        AllViewType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        AllViewDateStart.setCellValueFactory(new PropertyValueFactory<>("StartDateTime"));
        AllViewEndDate.setCellValueFactory(new PropertyValueFactory<>("EndDateTime"));
        AllViewCustID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        AllViewUserID.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        AllViewCon.setCellValueFactory(new PropertyValueFactory<>("ContactId"));
    }
}
