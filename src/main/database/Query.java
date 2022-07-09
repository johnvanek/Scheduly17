package main.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Query {
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;


    private static void makePreparedStatement(String strQuery) {
        try {
            preparedStatement = Connection.getConnection().prepareStatement(strQuery);
        } catch (SQLException e) {
            System.out.println("Error Making your Query's Prepared-Statement");
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public static void executeQuery(String str) {
        makePreparedStatement(str);
        try {
            resultSet = Objects.requireNonNull(getPreparedStatement()).executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void closeResultSet() {
        try {
            Objects.requireNonNull(getPreparedStatement()).close();
        } catch (SQLException e) {
            System.out.println("Error closing the Result Set ");
            throw new RuntimeException(e);
        }
    }

    private static void closePreparedStatement() {
        try {
            Objects.requireNonNull(getPreparedStatement()).close();
        } catch (SQLException e) {
            System.out.println("Error closing the PreparedStatement");
            throw new RuntimeException(e);
        }
    }

    public static void cleanupResources() {
        closeResultSet();
        closePreparedStatement();
    }

    public static ResultSet getResultSet() {
        return resultSet;
    }


}



