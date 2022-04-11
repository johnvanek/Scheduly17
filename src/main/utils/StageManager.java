package main.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager {
    private static Stage primaryStage;
    private static StageManager stageManager;

    public StageManager() {

    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        StageManager.primaryStage = primaryStage;
    }


    public static void setScene(String viewToLoad) {
        String loaderString = "../views/" + viewToLoad + ".fxml";
        try {
            Parent view = FXMLLoader.load(StageManager.class.getResource(loaderString));
            Scene scene = new Scene(view);
            setTitle(viewToLoad);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error loading fxml file: " + viewToLoad + " has occured");
        }
    }

    public static void setTitle(String title) {
        primaryStage.setTitle("Scheduly-17-" + Misc.makeFirstLetterUppercase(title));
    }
}
