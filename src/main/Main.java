package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("views/sample.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("views/login.fxml"));

        primaryStage.setTitle("Scheduly-17");
        Scene newScene = new Scene(root2);
        //newScene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());;
        primaryStage.setScene(newScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
    }
}
