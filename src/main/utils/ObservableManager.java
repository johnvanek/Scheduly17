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

public class ObservableManager {

    //Observable List's

    //Appointment View List's
    public static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointmentListWeek = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointmentListMonth = FXCollections.observableArrayList();

    // Appointment Add Screen List's

    // Appointment Add Screen Combo-Boxes
    public static ObservableList<LocalTime> addAppointmentStartTimes = FXCollections.observableArrayList();
    public static ObservableList<LocalTime> addAppointmentEndTimes = FXCollections.observableArrayList();

    //Customer List
    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();


    public static void createAppointmentList(String query) throws SQLException {

        Connection.makePreparedStatement(query, Connection.getConnection());
        PreparedStatement preparedStatement = Connection.getPreparedStatement();
        assert preparedStatement != null;
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println("Assembling your AppointmentList");
        System.out.println("******************************************************");
        System.out.println("Adding data to the AppointmentList");

        while (resultSet.next()) {
            //Take the data from the query and use it to populate the model

            // Technically because I'm not validating here the script that runs could add in appointments that overlap
            // And could add in appointments that are outside of hours but the rubric states only when adding or
            // Editing or else I would validate here each record that goes into observable list.

            //Temporary Appointment reference
            Appointment currentAppointment = new Appointment(resultSet.getInt("appointment_ID"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("location"),
                    resultSet.getString("type"),
                    resultSet.getTimestamp("start").toLocalDateTime(), //Convert to the LocalTime
                    resultSet.getTimestamp("end").toLocalDateTime(),
                    resultSet.getInt("customer_ID"),
                    resultSet.getInt("user_ID"),
                    resultSet.getInt("Contact_ID"));

            //Add all the appointments here from the script to the data model.
            appointmentList.add(currentAppointment);

            //Only add the weekly ones for weekly
            if (TimeManager.isInRangeWeekly(currentAppointment)) {
                appointmentListWeek.add(currentAppointment);
            }

            //Only add the Ones in the same month till the end of the month
            if (TimeManager.isInRangeMonthly(currentAppointment)) {
                appointmentListMonth.add(currentAppointment);
            }
        }
    }

    public static void createAppointmentWeekList(String query) {
        // TODO only add these if the Weekly validation is true.
        //if(TimeManager.)
    }

    public static void createAddAppointmentData(){
        createAddStartAppointmentComboBox();
        createAddEndAppointmentComboBox();
    }

    public static void createAddStartAppointmentComboBox(){
        addAppointmentStartTimes.addAll(
                LocalTime.of(8,0),
                LocalTime.of(8,15),
                LocalTime.of(8,30),
                LocalTime.of(8,45),
                LocalTime.of(9,0),
                LocalTime.of(9,15),
                LocalTime.of(9,30),
                LocalTime.of(9,45),
                LocalTime.of(10,0),
                LocalTime.of(10,15),
                LocalTime.of(10,30),
                LocalTime.of(10,45),
                LocalTime.of(11,0),
                LocalTime.of(11,15),
                LocalTime.of(11,30),
                LocalTime.of(11,45),
                LocalTime.of(12,0),
                LocalTime.of(12,15),
                LocalTime.of(12,30),
                LocalTime.of(12,45),
                LocalTime.of(13,0),
                LocalTime.of(13,15),
                LocalTime.of(13,30),
                LocalTime.of(13,45),
                LocalTime.of(14,0),
                LocalTime.of(14,15),
                LocalTime.of(14,30),
                LocalTime.of(14,45),
                LocalTime.of(15,0),
                LocalTime.of(15,15),
                LocalTime.of(15,30),
                LocalTime.of(15,45),
                LocalTime.of(16,0),
                LocalTime.of(16,15),
                LocalTime.of(16,30),
                LocalTime.of(16,45),
                LocalTime.of(17,0),
                LocalTime.of(17,15),
                LocalTime.of(17,30),
                LocalTime.of(17,45),
                LocalTime.of(18,0),
                LocalTime.of(18,15),
                LocalTime.of(18,30),
                LocalTime.of(18,45),
                LocalTime.of(19,0),
                LocalTime.of(19,15),
                LocalTime.of(19,30),
                LocalTime.of(19,45),
                LocalTime.of(20,0),
                LocalTime.of(20,15),
                LocalTime.of(20,30),
                LocalTime.of(20,45),
                LocalTime.of(21,0),
                LocalTime.of(21,15),
                LocalTime.of(21,30),
                LocalTime.of(21,45)
        );
    }

    public static void createAddEndAppointmentComboBox(){
        addAppointmentEndTimes.addAll(
                LocalTime.of(8,15),
                LocalTime.of(8,30),
                LocalTime.of(8,45),
                LocalTime.of(9,0),
                LocalTime.of(9,15),
                LocalTime.of(9,30),
                LocalTime.of(9,45),
                LocalTime.of(10,0),
                LocalTime.of(10,15),
                LocalTime.of(10,30),
                LocalTime.of(10,45),
                LocalTime.of(11,0),
                LocalTime.of(11,15),
                LocalTime.of(11,30),
                LocalTime.of(11,45),
                LocalTime.of(12,0),
                LocalTime.of(12,15),
                LocalTime.of(12,30),
                LocalTime.of(12,45),
                LocalTime.of(13,0),
                LocalTime.of(13,15),
                LocalTime.of(13,30),
                LocalTime.of(13,45),
                LocalTime.of(14,0),
                LocalTime.of(14,15),
                LocalTime.of(14,30),
                LocalTime.of(14,45),
                LocalTime.of(15,0),
                LocalTime.of(15,15),
                LocalTime.of(15,30),
                LocalTime.of(15,45),
                LocalTime.of(16,0),
                LocalTime.of(16,15),
                LocalTime.of(16,30),
                LocalTime.of(16,45),
                LocalTime.of(17,0),
                LocalTime.of(17,15),
                LocalTime.of(17,30),
                LocalTime.of(17,45),
                LocalTime.of(18,0),
                LocalTime.of(18,15),
                LocalTime.of(18,30),
                LocalTime.of(18,45),
                LocalTime.of(19,0),
                LocalTime.of(19,15),
                LocalTime.of(19,30),
                LocalTime.of(19,45),
                LocalTime.of(20,0),
                LocalTime.of(20,15),
                LocalTime.of(20,30),
                LocalTime.of(20,45),
                LocalTime.of(21,0),
                LocalTime.of(21,15),
                LocalTime.of(21,30),
                LocalTime.of(21,45),
                LocalTime.of(22, 0)
        );
    }

    public static ObservableList<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public static ObservableList<Customer> getCustomerList() {
        return customerList;
    }


}
