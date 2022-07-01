package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.database.Connection;
import main.utils.StageManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Main Class the Entry-Point into the application.
 */
public class Main extends Application {

    /**
     * This is the main method called by the JVM.
     * Execution of the application starts here.
     * Connects to database and launches arguments if provided to the JVM.
     *
     * @param args the list of string arguments that are passed to the JVM.
     */
    public static void main(String[] args) throws SQLException {
        Connection.makeConnection();
        //TODO - Get the Back End Hooked up the Front End.
        //  Create a method to see the database code in the terminal.
        //  Hook up the Cell Factory Method to show the data in the tables.
        //  Determine from there what models need to be created.
        // Implement a method to see the data from the database inside of the console.
        // The Method should be as follows
        // Using SQL through JDBC interfaces run a sql statement that returns a result.
        // For a test try to return the available appointments.
        // Just test this in main for now
        // Okay now that it is executing hook it up in the appropriate file locations.
        String testQuery = "SELECT * FROM appointments";
        Connection.makePreparedStatement(testQuery, Connection.getConnection());
        PreparedStatement preparedStatement = Connection.getPreparedStatement();
        assert preparedStatement != null; // This just makes intellij happy I think it is redundant.
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Printing out the results of test-Query");
        System.out.println("******************************************************");
        while (resultSet.next()) {
            System.out.println("TYPE: " + resultSet.getString("type") + ", ");
            System.out.println("Description: " + resultSet.getString("description"));
            System.out.println();
        }
        launch(args);
    }

    /**
     * This method set's up and displays the initial view for the JavaFx application.
     */
    @Override
    public void start(Stage primaryStage) {
        StageManager.setPrimaryStage(primaryStage);
        StageManager.setScene("login");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
