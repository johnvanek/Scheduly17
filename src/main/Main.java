package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.database.Connection;

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
    public static void main(String[] args) {
        Connection.makeConnection();
        launch(args);
    }

    /**
     * This method set's up and displays the initial view for the JavaFx application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        primaryStage.setTitle("Scheduly-17");
        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
