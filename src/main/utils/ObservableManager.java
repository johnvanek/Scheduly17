package main.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DAO.models.Appointment;
import main.DAO.models.Customer;
import main.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public final class ObservableManager {

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

    private ObservableManager() {
    }

    public static void populateDataAppointmentLists() {
        appointmentAllList.clear(); // Clear out the old List before creating a new one
        try {
            PreparedStatement ps = Connection.getConnection().prepareStatement("Select * FROM appointments");
            ResultSet rs = ps.getResultSet();
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
            //don't forget to clean up the resources
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error creating the Observable List for appointments");
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
                //TODO expand this to be all possible times at all possible hours
                // The range here should be 00:00 to 23:00
                // For the Close it should be shifted an hour so
                // 01:00 to 24:00 plus the 15 minute intervals
                // 12AM to 11PM
                // 1AM to 12PM
                //If Made in EST need to limit these to only the appropriate hours

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
        // TODO all more time here  // 01:00 to 24:00 plus the 15 minute intervals
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
