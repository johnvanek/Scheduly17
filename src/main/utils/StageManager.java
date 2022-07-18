package main.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.DAO.models.Appointment;
import main.DAO.models.Customer;
import main.controllers.UpdateAppointment;
import main.controllers.UpdateCustomer;

import java.io.IOException;
import java.util.Objects;

public final class StageManager {
    private static Stage primaryStage;

    private StageManager() {
    }

    private static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        StageManager.primaryStage = primaryStage;
    }

    private static void setScene(String viewToLoad) {
        String loaderString = "../views/" + viewToLoad + ".fxml";
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(StageManager.class.getResource(loaderString)));
            Scene scene = new Scene(view);
            setTitle(viewToLoad);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error loading fxml file: " + viewToLoad + " has occurred");
        }
    }

    private static void setTitle(String title) {
        primaryStage.setTitle("Scheduly-17-" + Misc.makeFirstLetterUppercase(title));
    }

    private static void setLogText(String scene) {
        System.out.println();
        System.out.println("[------------------Scene-Changing-To-" + scene + "------------------]");
        System.out.println();
    }

    public static void transitionNextScene(String nxtScene) {
        setTitle(nxtScene);
        setLogText(Misc.makeFirstLetterUppercase(nxtScene));
        setScene(nxtScene);
    }

    public static void transitionNextScene(String nxtScene, Appointment app) {
        setTitle(nxtScene);
        setLogText(Misc.makeFirstLetterUppercase(nxtScene));
        setScenePassDataUpdate(app);
    }

    public static void transitionNextScene(String nxtScene, Customer customer) {
        setTitle(nxtScene);
        setLogText(Misc.makeFirstLetterUppercase(nxtScene));
        setScenePassDataUpdate(customer);
    }

    private static void setScenePassDataUpdate(Appointment selected) {
        UpdateAppointment.appSelected = selected;
        setScene("updateAppointment");

    }

    private static void setScenePassDataUpdate(Customer selected) {
        UpdateCustomer.customerSelected = selected;
        setScene("updateCustomer");
    }
}
