package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.DAO.models.Appointment;
import main.DAO.models.Contact;
import main.DAO.models.Customer;
import main.database.Connection;
import main.utils.ObservableManager;
import main.utils.StageManager;

import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

import static main.utils.ObservableManager.*;

/**
 * This Class represents the controller logic for the view of the same name. The related view is shown
 * after navigating from the navbar and encompasses all functionality relating to Reports.
 */
public class Reports implements Initializable {
    //FXML ID'S********************************
    @FXML
    private TextField TotalTextBox;
    @FXML
    private ComboBox<Month> MonthSelectComboBox;
    @FXML
    private ComboBox<String> TypeSelectComboBox;
    @FXML
    private TableView<Appointment> CustomerScheduleView;
    @FXML
    private TableColumn<?, ?> CustomerScheduleAppId;
    @FXML
    private TableColumn<?, ?> CustomerScheduleTitle;
    @FXML
    private TableColumn<?, ?> CustomerScheduleType;
    @FXML
    private TableColumn<?, ?> CustomerScheduleLocation;
    @FXML
    private TableColumn<?, ?> CustomerScheduleStart;
    @FXML
    private TableColumn<?, ?> CustomerScheduleEnd;
    @FXML
    private TableColumn<?, ?> CustomerScheduleContactID;
    @FXML
    private ComboBox<Customer> CustomerSelectionComboBox;
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
    /**
     * Public constructor for reports only public so that javafx can instantiate it.
     */
    public Reports() {
    }

    //FXML Methods***************************************

    /**
     * Event handler for the navigation bar [Appointment]-> View calls StageManager to handle the transition to the
     * next scene. The method will route to the Main hub for appointments.
     * See the {@link StageManager StageManager} class for more details on how the transition occurs.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void changeSceneToAppointmentMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("appointments");
    }

    /**
     * Event handler for the navigation bar [Customer]-> View calls StageManager to handle the transition to the
     * next scene. The method will route to the Main hub for Customers.
     * See the {@link StageManager StageManager} class for more details on how the transition occurs.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void changeSceneToCustomerMainMenu(ActionEvent event) {
        StageManager.transitionNextScene("customers");
    }

    /**
     * Event handler for the navigation bar [Reports]-> View calls StageManager to handle the transition to the
     * next scene. The method will route to the Main hub for Reports.
     * See the {@link StageManager StageManager} class for more details on how the transition occurs.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void changeSceneToReports(ActionEvent event) {
        StageManager.transitionNextScene("reports");
    }

    /**
     * Event handler for the navigation bar [Signout]-> Quit loses the current Connection and exits the via the System.
     *
     * @param event Represents a ActionEvent event.
     */
    @FXML
    void terminateSession(ActionEvent event) {
        System.out.println("Terminating the application");
        Connection.closeConnection();
        System.exit(0);
    }

    /**
     * Using the values from the Month Combo-box and the Type-Combo-box this method assigns the Monthly total amount of the
     * selected type of appointments to the text field under the total label. The inner logic for calculation is performed by
     * {@link #calculateMonthTypeAppointments(Month, String) calculateMonthTypeAppointments}.
     */
    @FXML
    void determineSelectionReturnTotal() {
        if (MonthSelectComboBox.getValue() != null && TypeSelectComboBox.getValue() != null) {
            // first get the values of both of the boxs
            Month monthSelected = MonthSelectComboBox.getValue();
            String typeSelected = TypeSelectComboBox.getValue();

            TotalTextBox.setText(calculateMonthTypeAppointments(monthSelected, typeSelected));

            if (Integer.parseInt(TotalTextBox.getText()) > 0) {
                TotalTextBox.setStyle("-fx-text-fill: #24a6f4");
            } else TotalTextBox.setStyle("-fx-text-fill: black");

        }
    }

    /**
     * Populates the data for the Contact Schedule table view if a value is selected from the combo-box. Some contacts may
     * not have any data this is not an error, no appointment data simply exists for that contact.
     *
     * @param event Represents ActionEvent event.
     */
    @FXML
    void showSchedule(ActionEvent event) {
        if (ContactSelectionComboBox.getValue() != null) {
            ObservableManager.generateContactAppointmentList(ContactSelectionComboBox.getValue());
            ReportContactScheduleView.setItems(ContactAppointmentList);
            ReportContactScheduleAppIdColumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
            ReportContactScheduleTitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
            ReportContactScheduleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
            ReportContactScheduleDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
            ReportContactScheduleStartDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("StartDateTime"));
            ReportContactScheduleEndDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("EndDateTime"));
            ReportContactScheduleCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        }
    }

    /**
     * Populates the data for the Customer Schedule table view if a value is selected from the combo-box. Some customers may
     * not have any data this is not an error, no appointment data simply exists for that customer.
     *
     * @param event Represents ActionEvent event.
     */
    @FXML
    void showCustomerSchedule(ActionEvent event) {
        if (CustomerSelectionComboBox.getValue() != null) {
            ObservableManager.generateCustomerAppointmentList(CustomerSelectionComboBox.getValue());
            CustomerScheduleView.setItems(CustomerAppointmentList);
            CustomerScheduleAppId.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
            CustomerScheduleTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
            CustomerScheduleType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            CustomerScheduleLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
            CustomerScheduleStart.setCellValueFactory(new PropertyValueFactory<>("StartDateTime"));
            CustomerScheduleEnd.setCellValueFactory(new PropertyValueFactory<>("EndDateTime"));
            CustomerScheduleContactID.setCellValueFactory(new PropertyValueFactory<>("ContactId"));
        }
    }

    //Local Methods********************************

    /**
     * Given a month and a type this method calculates the total amount of each type appointment in a month.
     *
     * @param monthSelected A Month representing one of the 12 months.
     * @param typeSelected  A Type representing a type of appointment. All Types is special in that the count that is
     *                      returned will allow for every type to be added to the total amount.
     * @return A String representation an integer that represents the total amount of the type appointments in a Month.
     *
     * <p><b>Lambda [4]</b> - Uses the stream.filter()
     * with a predicate condition that matches for each appointment in that if the appointment matches the type and the
     * month selected. Terminated by the count() terminal operation which then returns to a variable count that is returned
     * as a String by the method. Very similar to lambda 2 and 3 except for the terminal operation count()
     * would not consider unique.
     * </p>
     */
    String calculateMonthTypeAppointments(Month monthSelected, String typeSelected) {
        int count;
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

    /**
     * Initializes the Reports scene called after the FXML Fields have been loaded and injected. Initializes and
     * populates the Front end data for a list of Months, list of types . The table views are representation of Customers data and
     * Contact data from the
     * observable lists in {@link ObservableManager}.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
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

        //Third Tab
        ObservableManager.populateDataCustomerList();
        CustomerSelectionComboBox.setItems(CustomerList);
    }
}
