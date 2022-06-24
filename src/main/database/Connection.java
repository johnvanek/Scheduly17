package main.database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    private static java.sql.Connection connection = null;  // Connection Interface
    private static PreparedStatement preparedStatement;

    public static void makeConnection() {

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error:" + e.getMessage() + " Class Not Found");
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage() + " General Sql Exception");
        }
    }

    public static java.sql.Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void makePreparedStatement(String sqlStatement, java.sql.Connection conn) throws SQLException {
        if (conn != null) preparedStatement = conn.prepareStatement(sqlStatement);
        else System.out.println("Prepared Statement Creation Failed!");
    }

    public static PreparedStatement getPreparedStatement() {
        if (preparedStatement != null) return preparedStatement;
        else System.out.println("Prepared Statement Reference is null");
        return null;
    }


}