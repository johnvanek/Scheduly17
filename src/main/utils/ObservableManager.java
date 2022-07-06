package main.utils;

import main.DAO.models.Appointment;
import main.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObservableManager {
    public static void CreateObservable(String query, String type) throws SQLException {
        String[] columnList = TableHelper.getAppointmentList();
        Connection.makePreparedStatement(query, Connection.getConnection());
        PreparedStatement preparedStatement = Connection.getPreparedStatement();
        assert preparedStatement != null;
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Assembling your Observable");
        System.out.println("******************************************************");
        while (resultSet.next()) {
            // TODO This is where we would add the code to create new Model Objects
            System.out.println("Adding data to the Observable");

        }
    }

}
