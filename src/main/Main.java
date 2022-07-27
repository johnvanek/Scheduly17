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
        launch(args);
        Connection.closeConnection();
    }

    /**
     * Starts the JAVAFX application this method set's up and displays the initial view for the JavaFx application.
     */
    @Override
    public void start(Stage primaryStage) {
        StageManager.setPrimaryStage(primaryStage);
        StageManager.transitionNextScene("login");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
