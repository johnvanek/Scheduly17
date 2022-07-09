package main.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DAO.models.Appointment;
import main.DAO.models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import static main.database.Query.*;

public abstract class ObservableManager {

    //Observable List's
    //Appointment View List's
    public static ObservableList<Appointment> appointmentAllList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointmentWeeklyList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointmentMonthlyList = FXCollections.observableArrayList();

    // Appointment Add Screen Combo-Boxes
    public static ObservableList<LocalTime> StartTimesAddApp = FXCollections.observableArrayList();
    public static ObservableList<LocalTime> EndTimesAddApp = FXCollections.observableArrayList();

    //Customer List
    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    //Now that we are done with the ResultSet lets cleanup


    public static void populateDataAppointmentLists() {
        appointmentAllList.clear(); // Clear out the old List before creating a new one
        executeQuery("Select * FROM appointments");
        ResultSet rs = getResultSet();

        try {
            while (rs.next()) {
                //Temporary Appointment reference
                Appointment currentAppointment = new Appointment(rs.getInt("appointment_ID"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("type"),
                        rs.getTimestamp("start").toLocalDateTime(), //Convert to the LocalTime
                        rs.getTimestamp("end").toLocalDateTime(),
                        rs.getInt("customer_ID"),
                        rs.getInt("user_ID"),
                        rs.getInt("Contact_ID"));


                //Add all the appointments here from the script to the data model.
                appointmentAllList.add(currentAppointment);

                //Only add the weekly ones for weekly
                if (TimeManager.isInRangeWeekly(currentAppointment)) {
                    appointmentWeeklyList.add(currentAppointment);
                }

                //Only add the Ones in the same month till the end of the month
                if (TimeManager.isInRangeMonthly(currentAppointment)) {
                    appointmentMonthlyList.add(currentAppointment);
                }
            }
            cleanupResources();
        } catch (SQLException e) {
            System.out.println("There was a problem putting the information for appointments from the database" +
                    "into the observableList for the appointmentsAll table-view");
            throw new RuntimeException(e);
        }

    }


    public static void populateDataComboBoxes() {
        //Clear out the old data from the combo-Boxes before adding new data.
        ObservableManager.StartTimesAddApp.clear();
        ObservableManager.EndTimesAddApp.clear();
        createAddStartAppointmentComboBox();
        createAddEndAppointmentComboBox();
    }

    public static void createAddStartAppointmentComboBox() {
        StartTimesAddApp.addAll(
                LocalTime.of(8, 0),
                LocalTime.of(8, 15),
                LocalTime.of(8, 30),
                LocalTime.of(8, 45),
                LocalTime.of(9, 0),
                LocalTime.of(9, 15),
                LocalTime.of(9, 30),
                LocalTime.of(9, 45),
                LocalTime.of(10, 0),
                LocalTime.of(10, 15),
                LocalTime.of(10, 30),
                LocalTime.of(10, 45),
                LocalTime.of(11, 0),
                LocalTime.of(11, 15),
                LocalTime.of(11, 30),
                LocalTime.of(11, 45),
                LocalTime.of(12, 0),
                LocalTime.of(12, 15),
                LocalTime.of(12, 30),
                LocalTime.of(12, 45),
                LocalTime.of(13, 0),
                LocalTime.of(13, 15),
                LocalTime.of(13, 30),
                LocalTime.of(13, 45),
                LocalTime.of(14, 0),
                LocalTime.of(14, 15),
                LocalTime.of(14, 30),
                LocalTime.of(14, 45),
                LocalTime.of(15, 0),
                LocalTime.of(15, 15),
                LocalTime.of(15, 30),
                LocalTime.of(15, 45),
                LocalTime.of(16, 0),
                LocalTime.of(16, 15),
                LocalTime.of(16, 30),
                LocalTime.of(16, 45),
                LocalTime.of(17, 0),
                LocalTime.of(17, 15),
                LocalTime.of(17, 30),
                LocalTime.of(17, 45),
                LocalTime.of(18, 0),
                LocalTime.of(18, 15),
                LocalTime.of(18, 30),
                LocalTime.of(18, 45),
                LocalTime.of(19, 0),
                LocalTime.of(19, 15),
                LocalTime.of(19, 30),
                LocalTime.of(19, 45),
                LocalTime.of(20, 0),
                LocalTime.of(20, 15),
                LocalTime.of(20, 30),
                LocalTime.of(20, 45),
                LocalTime.of(21, 0),
                LocalTime.of(21, 15),
                LocalTime.of(21, 30),
                LocalTime.of(21, 45)
        );
    }

    public static void createAddEndAppointmentComboBox() {
        EndTimesAddApp.addAll(
                LocalTime.of(8, 15),
                LocalTime.of(8, 30),
                LocalTime.of(8, 45),
                LocalTime.of(9, 0),
                LocalTime.of(9, 15),
                LocalTime.of(9, 30),
                LocalTime.of(9, 45),
                LocalTime.of(10, 0),
                LocalTime.of(10, 15),
                LocalTime.of(10, 30),
                LocalTime.of(10, 45),
                LocalTime.of(11, 0),
                LocalTime.of(11, 15),
                LocalTime.of(11, 30),
                LocalTime.of(11, 45),
                LocalTime.of(12, 0),
                LocalTime.of(12, 15),
                LocalTime.of(12, 30),
                LocalTime.of(12, 45),
                LocalTime.of(13, 0),
                LocalTime.of(13, 15),
                LocalTime.of(13, 30),
                LocalTime.of(13, 45),
                LocalTime.of(14, 0),
                LocalTime.of(14, 15),
                LocalTime.of(14, 30),
                LocalTime.of(14, 45),
                LocalTime.of(15, 0),
                LocalTime.of(15, 15),
                LocalTime.of(15, 30),
                LocalTime.of(15, 45),
                LocalTime.of(16, 0),
                LocalTime.of(16, 15),
                LocalTime.of(16, 30),
                LocalTime.of(16, 45),
                LocalTime.of(17, 0),
                LocalTime.of(17, 15),
                LocalTime.of(17, 30),
                LocalTime.of(17, 45),
                LocalTime.of(18, 0),
                LocalTime.of(18, 15),
                LocalTime.of(18, 30),
                LocalTime.of(18, 45),
                LocalTime.of(19, 0),
                LocalTime.of(19, 15),
                LocalTime.of(19, 30),
                LocalTime.of(19, 45),
                LocalTime.of(20, 0),
                LocalTime.of(20, 15),
                LocalTime.of(20, 30),
                LocalTime.of(20, 45),
                LocalTime.of(21, 0),
                LocalTime.of(21, 15),
                LocalTime.of(21, 30),
                LocalTime.of(21, 45),
                LocalTime.of(22, 0)
        );
    }

}
