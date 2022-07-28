package main.database;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is a utility that is responsible for handling the logic relating to connection to a database.
 */
public class Connection {
    /**
     * a string representing the protocol
     */
    private static final String protocol = "jdbc";
    /**
     * a string representing the vendor
     */
    private static final String vendor = ":mysql:";
    /**
     * a string representing the location of the database
     */
    private static final String location = "//localhost/";
    /**
     * a string representing the database name
     */
    private static final String databaseName = "client_schedule";
    /**
     * a string that combines to form the jdbc uniform resource location
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    /**
     * a string that represents the jdbc driver
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    /**
     * a string that represents the database user's username
     */
    private static final String userName = "sqlUser"; // Username
    /**
     * a string that represents the database user's password
     */
    private static final String password = "Passw0rd!"; // Password
    /**
     * a connection object that serves to store the state of the current connection
     */
    private static java.sql.Connection connection = null;  // Connection Interface

    /**
     * This method initializes the connection for the jdbc to connect to the database.
     */
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

    /**
     * Returns a reference to the current connection if one has been assigned else returns a null.
     *
     * @return a connection object representing a reference to the current connection
     */
    public static java.sql.Connection getConnection() {
        return connection;
    }

    /**
     * Closes the current active connection to save resources
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}