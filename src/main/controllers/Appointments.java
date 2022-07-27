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
import main.DAO.models.Appointment;
import main.database.Connection;
import main.utils.ObservableManager;
import main.utils.StageManager;
import main.utils.TimeManager;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.*;

/**
 * This Class represents the controller logic for the view of the same name. This is the first scene show
 * after logging in and encompasses all functionality relating to appointments.
 */
public class Appointments implements Initializable {
    //FXML-ID'S*******************************
    //            Week-Tab - ID'S
    //****************************************
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
    //****************************************
    //          Month-Tab - ID'S
    //****************************************
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
    //****************************************
    //          Modify-Tab - ID'S
    //****************************************
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

    //****************************************
    //             FXML-METHODS
    //****************************************

    /**
     * Deselects if currently selected a table row from the AllView-tableview. By getting the selection and then clearing
     * out the selection.
     *
     * @param event Represents a mouse event.
     */
    @FXML
    void deselectTableRow(MouseEvent event) {
        AllView.getSelectionModel().clearSelection();
    }

    /**
     * Attempts to delete a selected appointment via JDBC if the user agrees to continue. Regardless of
     * whether the deletion was successful or not the front-end data
     * models get recreated as a result of potentially altering the records.
     *
     * @param event Represents a mouse event.
     */
    @FXML
    void deleteFromAll(MouseEvent event) {
        //The Alert Code
        Alert confirmDelete = new Alert(Alert.AlertType.WARNING);
        confirmDelete.setHeaderText("[WARNING] : [DELETION]");
        confirmDelete.getButtonTypes().add(ButtonType.CANCEL);
        confirmDelete.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Appointment selectedAppointment = null;
        //If there is a current selection in the all view
        if (AllView.getSelectionModel().getSelectedItem() != null) {
            selectedAppointment = AllView.getSelectionModel().getSelectedItem();
            confirmDelete.setContentText("[Warning!] - You are about to delete Appointment[ID]: " +
                    selectedAppointment.getAppointmentId() + " . " +
                    "Which is of type " + selectedAppointment.getType() + " " +
                    "are you sure you wish to continue? This action cannot be reversed!");
            //TODO document this lambda
            Appointment effectivelyFinalSelectedAppointment = selectedAppointment;
            confirmDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String query = "Delete From appointments Where Appointment_ID = " +
                            effectivelyFinalSelectedAppointment.getAppointmentId();
                    try {
                        PreparedStatement ps = Connection.getConnection().prepareStatement(query);
                        System.out.println(ps.executeUpdate() + " database records have been deleted.");
                        ps.close();
                        //When deletion does happen repopulate the tables
                        populateDataAppointmentLists();
                    } catch (SQLException e) {
                        System.out.println("There was a problem trying to perform a deletion on the modify All Appointments tab");
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    /**
     * Changes the scene to the Update Appointment scene. Handles the mouse event, if the selection from AllView is valid
     * update calls the StageManager to handle the scene changing.
     *
     * @param event Represents a mouse event.
     */
    @FXML
    void changeSceneToUpdateAppointment(MouseEvent event) {
        if (AllView.getSelectionModel().getSelectedItem() != null) {
            Appointment selection = AllView.getSelectionModel().getSelectedItem();
            StageManager.transitionNextScene("updateAppointment", selection);
        }
    }

    /**
     * Changes the scene to the Appointment main hub area. Calls StageManager to handle the actual transition to the
     * next scene. See the {@link StageManager StageManager} class for more details on how the transition occurs.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void changeSceneToAppointmentMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("appointments");
    }

    /**
     * Changes the scene to the Customer main hub area. Calls StageManager to handle the actual transition to the
     * next scene.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void changeSceneToCustomerMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("customers");
    }

    @FXML
    void changeSceneToReports(ActionEvent event) {
        StageManager.transitionNextScene("reports");
    }

    @FXML
    void terminateSession(ActionEvent event) {
        System.out.println("Terminating the application");
        Connection.closeConnection();
        System.exit(0);
    }

    @FXML
    void changeSceneToAddAppointment(MouseEvent event) {
        StageManager.transitionNextScene("addAppointment");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableManager.populateDataAppointmentLists();
        //Bind the Table Views
        //Initialize the Month Tab
        WeekView.setItems(AppointmentWeeklyList);
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
        MonthView.setItems(AppointmentMonthlyList);
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
        AllView.setItems(AppointmentAllList);
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

        //Testing LoggedInUser
        System.out.println("The currently LoggedIn user is " + currentlyLoggedInUser);
    }
}
