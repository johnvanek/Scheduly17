package main.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DAO.models.Appointment;
import main.DAO.models.Customer;
import main.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObservableManager {

    //Create Observable List
    public static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointmentListWeek = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointmentListMonth = FXCollections.observableArrayList();

    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    static {

    }
    public static void CreateAppointmentList(String query) throws SQLException {

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
            Appointment currentAppointment = new Appointment( resultSet.getInt("appointment_ID"),
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
            if(TimeManager.isInRangeWeekly(currentAppointment)){
                appointmentListWeek.add(currentAppointment);
            }

            //Only add the Ones in the same month till the end of the month
            if(TimeManager.isInRangeMonthly(currentAppointment)){
                appointmentListMonth.add(currentAppointment);
            }
        }
    }

    public static void CreateAppointmentWeekList(String query){
        // TODO only add these to the list if the
        //if(TimeManager.)
    }

    public static ObservableList<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public static ObservableList<Customer> getCustomerList() {
        return customerList;
    }


}
