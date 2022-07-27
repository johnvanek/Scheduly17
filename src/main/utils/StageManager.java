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

/**
 * This Class represents a utility feature responsible for handling any logic relating to manipulating the JavaFX: Primary
 * Stage and ensuing scenes.
 */
public final class StageManager {
    /**
     * The Primary Stage of the JavaFX application representative of a theater stage in a play. Where a scene may change
     * from act to act the stage remains the same essentially containing the scenes and deciding which to show and which
     * to keep back behind the curtain until needed.
     */
    private static Stage primaryStage;


    /**
     * The private Constructor for Stage Manager combined with the final class definition for StageManger this ensures
     * that the Class cannot be extended or instantiated.
     */
    private StageManager() {
    }

    /**
     * Returns a reference to the Primary Stage.
     *
     * @return A Stage representing the primary stage of the JavaFX application.
     */
    private static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Assigns the value of the Primary Stage.
     *
     * @param primaryStage A Stage representing the primary stage of the JavaFX application.
     */
    public static void setPrimaryStage(Stage primaryStage) {
        StageManager.primaryStage = primaryStage;
    }

    /**
     * Assigns the Scene of the Primary Stage. Also sets the Title for the scene in the application window up top.
     * Calling this method will change the Scene of the JavaFX: Application.
     *
     * @param viewToLoad A String representing the name of the view that is to be loaded.
     */
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

    /**
     * Assigns the Title that is to be shown at the top of the application window.
     *
     * @param title A String representing the Title shown at the top of the application window
     */
    private static void setTitle(String title) {
        primaryStage.setTitle("Scheduly-17-" + Misc.makeFirstLetterUppercase(title));
    }

    /**
     * A utility method that assigns the Log message that is to be displayed in between scene transitions.
     *
     * @param scene A String representing the name of the scene that is being transitioned to.
     */
    private static void setLogText(String scene) {
        System.out.println();
        System.out.println("[------------------Scene-Changing-To-" + scene + "------------------]");
        System.out.println();
    }

    /**
     * A bundled utility method that calls the methods {@link #setTitle(String) setTitle}, {@link #setLogText(String) setLogText},
     * and {@link #setScene(String) setScene} in one location. This ensures that other classes calling StageManager only
     * have to call the one outside method to handle all the logic relating to scene-changing. And they simply only need
     * to provide the name of the desired scene.
     *
     * @param nxtScene A String representing the scene that is to be transitioned to.
     */
    public static void transitionNextScene(String nxtScene) {
        setTitle(nxtScene);
        setLogText(Misc.makeFirstLetterUppercase(nxtScene));
        setScene(nxtScene);
    }

    /**
     * Transition to the next scene using the supplied string and pass appointment data to the scene
     * that is being transitioned to.
     *
     * @param nxtScene A String representing the scene that is to be transitioned to.
     * @param app      An Appointment that represents appointment data from the database.
     */
    public static void transitionNextScene(String nxtScene, Appointment app) {
        setTitle(nxtScene);
        setLogText(Misc.makeFirstLetterUppercase(nxtScene));
        setScenePassDataUpdate(app);
    }

    /**
     * Transition to the next scene using the supplied string and pass customer data to the scene
     * that is being transitioned to.
     *
     * @param nxtScene A String representing the scene that is to be transitioned to.
     * @param customer A Customer that represents customer data from the database.
     */
    public static void transitionNextScene(String nxtScene, Customer customer) {
        setTitle(nxtScene);
        setLogText(Misc.makeFirstLetterUppercase(nxtScene));
        setScenePassDataUpdate(customer);
    }

    /**
     * Transitions the scene to the updateAppointment scene while passing along a selected
     * Appointment.
     *
     * @param selected An Appointment representing a selected Appointment from a Table Row.
     */
    private static void setScenePassDataUpdate(Appointment selected) {
        UpdateAppointment.appSelected = selected;
        setScene("updateAppointment");
    }

    /**
     * Transitions the scene to the updateCustomer scene while passing along a selected
     * Appointment.
     *
     * @param selected An Appointment representing a selected Customer from a Table Row.
     */
    private static void setScenePassDataUpdate(Customer selected) {
        UpdateCustomer.customerSelected = selected;
        setScene("updateCustomer");
    }
}
