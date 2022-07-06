package main.utils;

import main.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObservableManager {
    public static void CreateObservable(String query) throws SQLException {
        Connection.makePreparedStatement(query, Connection.getConnection());
        PreparedStatement preparedStatement = Connection.getPreparedStatement();
        assert preparedStatement != null;
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Assembling your Observable");
        System.out.println("******************************************************");
        while (resultSet.next()) {
            String[] columnList = TableHelper.getAppointmentList();

            // TODO This is where we would add the code to create the cell factory.
            System.out.println("Adding data to the Observable From Database");
        }
    }

}
