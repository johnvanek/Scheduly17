package main.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DAO.models.Appointment;
import main.DAO.models.Customer;
import main.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public final class ObservableManager {

    //Observable List's
    //Appointment View List's
    public static ObservableList<Appointment> appointmentAllList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointmentWeeklyList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointmentMonthlyList = FXCollections.observableArrayList();
    // Appointment Add Screen Combo-Boxes
    public static ObservableList<LocalTime> StartTimesAddApp = FXCollections.observableArrayList();
    public static ObservableList<LocalTime> EndTimesAddApp = FXCollections.observableArrayList();

//    public static ObservableList<LocalDateTime> StartDateTimesAddApp = FXCollections.observableArrayList();
//
//    public static ObservableList<LocalTime> EndDateTimesAddApp = FXCollections.observableArrayList();
    //Customer List
    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    private ObservableManager() {
    }

    public static void populateDataAppointmentLists() {
        appointmentAllList.clear(); // Clear out the old List before creating a new one

        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "Select * From appointments";
        try {
            ps = Connection.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
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
                //cleanup
                rs.close();
                ps.close();
            }
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
                //These are all the available times In local.
                LocalTime.of(0, 0),
                LocalTime.of(0, 15),
                LocalTime.of(0, 30),
                LocalTime.of(0, 45),
                LocalTime.of(1, 0),
                LocalTime.of(1, 15),
                LocalTime.of(1, 30),
                LocalTime.of(1, 45),
                LocalTime.of(2, 0),
                LocalTime.of(2, 15),
                LocalTime.of(2, 30),
                LocalTime.of(2, 45),
                LocalTime.of(3, 0),
                LocalTime.of(3, 15),
                LocalTime.of(3, 30),
                LocalTime.of(3, 45),
                LocalTime.of(4, 0),
                LocalTime.of(4, 15),
                LocalTime.of(4, 30),
                LocalTime.of(4, 45),
                LocalTime.of(5, 0),
                LocalTime.of(5, 15),
                LocalTime.of(5, 30),
                LocalTime.of(5, 45),
                LocalTime.of(6, 0),
                LocalTime.of(6, 15),
                LocalTime.of(6, 30),
                LocalTime.of(6, 45),
                LocalTime.of(7, 0),
                LocalTime.of(7, 15),
                LocalTime.of(7, 30),
                LocalTime.of(7, 45),
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
                LocalTime.of(21, 45),
                LocalTime.of(22, 0),
                LocalTime.of(22, 15),
                LocalTime.of(22, 30),
                LocalTime.of(22, 45),
                LocalTime.of(23, 0),
                LocalTime.of(23, 15),
                LocalTime.of(23, 30),
                LocalTime.of(23, 45)
        );
    }

    public static void createAddEndAppointmentComboBox() {
        EndTimesAddApp.addAll(
                LocalTime.of(0, 30),
                LocalTime.of(0, 45),
                LocalTime.of(1, 0),
                LocalTime.of(1, 15),
                LocalTime.of(1, 30),
                LocalTime.of(1, 45),
                LocalTime.of(2, 0),
                LocalTime.of(2, 15),
                LocalTime.of(2, 30),
                LocalTime.of(2, 45),
                LocalTime.of(3, 0),
                LocalTime.of(3, 15),
                LocalTime.of(3, 30),
                LocalTime.of(3, 45),
                LocalTime.of(4, 0),
                LocalTime.of(4, 15),
                LocalTime.of(4, 30),
                LocalTime.of(4, 45),
                LocalTime.of(5, 0),
                LocalTime.of(5, 15),
                LocalTime.of(5, 30),
                LocalTime.of(5, 45),
                LocalTime.of(6, 0),
                LocalTime.of(6, 15),
                LocalTime.of(6, 30),
                LocalTime.of(6, 45),
                LocalTime.of(7, 0),
                LocalTime.of(7, 15),
                LocalTime.of(7, 30),
                LocalTime.of(7, 45),
                LocalTime.of(8, 0),
                LocalTime.of(8, 15),
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
                LocalTime.of(22, 0),
                LocalTime.of(22, 15),
                LocalTime.of(22, 30),
                LocalTime.of(22, 45),
                LocalTime.of(23, 0),
                LocalTime.of(23, 15),
                LocalTime.of(23, 30),
                LocalTime.of(23, 45),
                LocalTime.of(0, 0)
        );
    }

}
