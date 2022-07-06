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
            appointmentList.add(new Appointment( resultSet.getInt("appointment_ID"),
                    // TODO Time stamp is in UTC but should display in Local Time Zone
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("location"),
                    resultSet.getString("type"),
                    resultSet.getTimestamp("start").toLocalDateTime(),
                    resultSet.getTimestamp("end").toLocalDateTime(),
                    resultSet.getInt("customer_ID"),
                    resultSet.getInt("user_ID"),
                    resultSet.getInt("Contact_ID")));
        }
    }

    public static ObservableList<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public static ObservableList<Customer> getCustomerList() {
        return customerList;
    }

    public static ObservableList<?> ListFinder(String type) {
        if ((type.equals("appointment"))) {
            return getAppointmentList();
        } else if (type.equals("customer")) {
            return getCustomerList();
        } else return null;
    }
}
