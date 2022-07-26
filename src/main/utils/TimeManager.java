package main.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import main.DAO.models.Appointment;
import main.database.Connection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.stream.Collectors;

import static main.utils.ObservableManager.*;

public final class TimeManager {

    private TimeManager() {
    }

    public static void checkForUpcomingAppointment() {
        //The Alert are defined at the top of the namespace
        Alert noUpcomingAlert = new Alert(Alert.AlertType.INFORMATION, "This is a message to " +
                "inform you that user[" + currentlyLoggedInUser.getUserName() + "] does not have any upcoming " +
                "appointments within the next 15 minutes.", ButtonType.OK);

        noUpcomingAlert.setHeaderText("[INFO] : [TIME]");
        noUpcomingAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Alert lessThanFifteenAlert; //Defined as needed

        boolean flag = true; // flag if set to false stops the loop over all appointments

        for (Appointment app : AppointmentAllList) {
            Duration timeTillAppointmentStartInMinutes = Duration.between(LocalDateTime.now(), app.getStartDateTime());
            long timeDifferenceMinutes = timeTillAppointmentStartInMinutes.toMinutes();
            if (timeDifferenceMinutes > 0 && timeDifferenceMinutes <= 15) {
                lessThanFifteenAlert = new Alert(Alert.AlertType.WARNING, "Warning you have an appointment within " +
                        "the next " + timeDifferenceMinutes + "-minutes. Appointment: ID[" + app.getAppointmentId() + "] " +
                        "Date: " + LocalDate.from(app.getStartDateTime()) + " Time: " + LocalTime.from(app.getStartDateTime())
                        + ".", ButtonType.OK);
                lessThanFifteenAlert.setHeaderText("[WARNING] : [TIME]");
                lessThanFifteenAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                lessThanFifteenAlert.showAndWait();
                flag = false;
                break;
            }
        }
        if (flag) noUpcomingAlert.showAndWait();
    }

    public static void isAppointmentInWithinFifteenMinutes(Appointment app) {

    }

    //Might need to figure out the current work week.
    public static boolean isInRangeWeekly(Appointment appointment) {
        //This just checks if it is within 7 days
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime appStartDateTime = appointment.getStartDateTime();
        LocalDateTime sevenDaysForward = currentTime.plusDays(7);

        return ((currentTime.isBefore(appStartDateTime) && (appStartDateTime.isBefore(sevenDaysForward) && appStartDateTime.isAfter(currentTime))));
    }

    public static boolean isInRangeMonthly(Appointment appointment) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime appStartDateTime = appointment.getStartDateTime();
        LocalDateTime monthForward = currentTime.plusMonths(1);
        //If they have the same time going forward up to a month but also have the same year and month return true
        return (currentTime.isBefore(appStartDateTime) && currentTime.isBefore(monthForward) && appStartDateTime.isAfter(currentTime) && currentTime.getMonth() == appStartDateTime.getMonth() && currentTime.getYear() == appStartDateTime.getYear());
    }

    public static boolean isCustomerAvailable(int customerID, LocalDateTime appStart, LocalDateTime appEnd) throws SQLException {
        //Methods in the LocalDateTime class
        // A > B    /// A.isAfter(B)
        // A == B  ///  A.isEqual(B)
        // A < B   ///  A.isBefore(B)

        //To get A <= B
        // In LocalDateTimeMethods
        // (A.isBefore(B) || A.isEqual(B))

        //TO get A >= B
        //InLocalDateTimeMethods
        // A.isAfter(B) || A.isEqual(B)

        String query = "SELECT * FROM appointments WHERE Customer_ID = " + customerID;
        PreparedStatement ps = Connection.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        System.out.println("Comparing appointment times for potential customer overlap!!");
        Alert overlapAlert = new Alert(Alert.AlertType.ERROR, "[Overlap-Error] The appointment you tried to add would overlap " +
                "the Customer with the Customer_ID of  " + customerID + " with another existing appointment.", ButtonType.CLOSE);
        overlapAlert.setHeaderText("[CANNOT-ADD] : [REASON]");

        //Resize the alert so the text does not get cut off
        overlapAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        while (rs.next()) {
            Appointment currentAppointment = new Appointment(rs.getInt("appointment_ID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("type"),
                    rs.getTimestamp("start").toLocalDateTime(), //Convert to the LocalTime
                    rs.getTimestamp("end").toLocalDateTime(),
                    rs.getInt("customer_ID"),
                    rs.getInt("user_ID"),
                    rs.getInt("Contact_ID"));

            //Get the data to compare from the records that have the same customer_ID
            LocalDateTime databaseStart = currentAppointment.getStartDateTime();
            LocalDateTime databaseEnd = currentAppointment.getEndDateTime();
            //Then check if there are any overlaps edge cases. If any of these occur disregard the appointment
            //Condition 1 --|The start of this meeting is during another existing meeting|--
            if ((appStart.isAfter(databaseStart) || appStart.isEqual(databaseStart)) && (appStart.isBefore(databaseEnd))) {
                overlapAlert.showAndWait();
                //Clear the current values out of the appointment
//                StageManager.setTitle("appointments");
//                StageManager.setScene("appointments");
                return false;
            }
            //Condition 2 --|The appointment End is in between another existing meeting|--
            // (WantToAddEnd > DatabaseStart && WantToAddEnd <= DatabaseEnd)
            if ((appEnd.isAfter(databaseStart) && (appEnd.isBefore(databaseEnd) || appEnd.isEqual(databaseEnd)))) {
                overlapAlert.showAndWait();
//                StageManager.setTitle("appointments");
//                StageManager.setScene("appointments");
                return false;
            }
            //Condition 3 --|Start and end consumes another smaller meeting completely|--
            if ((appStart.isBefore(databaseStart) || appStart.isEqual(databaseStart))
                    && (appEnd.isAfter(databaseEnd) || appEnd.isEqual(databaseEnd))) {
                overlapAlert.showAndWait();
//                StageManager.setTitle("appointments");
//                StageManager.setScene("appointments");
                return false;
            }
        }
        return true;
    }


    public static boolean isCustomerAvailableForUpdate(int customerID, LocalDateTime appStart, LocalDateTime appEnd, String title, String description, int appId) throws SQLException {
        //Methods in the LocalDateTime class
        // A > B    /// A.isAfter(B)
        // A == B  ///  A.isEqual(B)
        // A < B   ///  A.isBefore(B)

        //To get A <= B
        // In LocalDateTimeMethods
        // (A.isBefore(B) || A.isEqual(B))

        //TO get A >= B
        //InLocalDateTimeMethods
        // A.isAfter(B) || A.isEqual(B)

        //Select all the record for comparison except the current record
        String query = "SELECT * FROM appointments " +
                "WHERE  Appointment_ID not in " +
                "(" +
                "Select Appointment_ID " +
                "FROM appointments " +
                "WHERE Appointment_ID = " + appId +
                ")";

        PreparedStatement ps = Connection.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        //Cant use query here that is a update statement

        System.out.println("Comparing appointment times for potential customer overlap!!");
        Alert overlapAlert = new Alert(Alert.AlertType.ERROR, "[Overlap-Error] The appointment you tried to add would overlap " +
                "the Customer with the Customer_ID of  " + customerID + " with another existing appointment.", ButtonType.CLOSE);
        overlapAlert.setHeaderText("[CANNOT-ADD] : [REASON]");

        //Resize the alert so the text does not get cut off
        overlapAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        while (rs.next()) {
            Appointment currentAppointment = new Appointment(rs.getInt("appointment_ID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("type"),
                    rs.getTimestamp("start").toLocalDateTime(), //Convert to the LocalTime
                    rs.getTimestamp("end").toLocalDateTime(),
                    rs.getInt("customer_ID"),
                    rs.getInt("user_ID"),
                    rs.getInt("Contact_ID"));

            //Get the data to compare from the records that have the same customer_ID
            LocalDateTime databaseStart = currentAppointment.getStartDateTime();
            LocalDateTime databaseEnd = currentAppointment.getEndDateTime();
            //Then check if there are any overlaps edge cases. If any of these occur disregard the appointment
            //Condition 1 --|The start of this meeting is during another existing meeting|--
            if ((appStart.isAfter(databaseStart) || appStart.isEqual(databaseStart)) && (appStart.isBefore(databaseEnd))) {
                overlapAlert.showAndWait();
                //Clear the current values out of the appointment
//                StageManager.setTitle("appointments");
//                StageManager.setScene("appointments");
                return false;
            }
            //Condition 2 --|The appointment End is in between another existing meeting|--
            // (WantToAddEnd > DatabaseStart && WantToAddEnd <= DatabaseEnd)
            if ((appEnd.isAfter(databaseStart) && (appEnd.isBefore(databaseEnd) || appEnd.isEqual(databaseEnd)))) {
                overlapAlert.showAndWait();
//                StageManager.setTitle("appointments");
//                StageManager.setScene("appointments");
                return false;
            }
            //Condition 3 --|Start and end consumes another smaller meeting completely|--
            if ((appStart.isBefore(databaseStart) || appStart.isEqual(databaseStart))
                    && (appEnd.isAfter(databaseEnd) || appEnd.isEqual(databaseEnd))) {
                overlapAlert.showAndWait();
//                StageManager.setTitle("appointments");
//                StageManager.setScene("appointments");
                return false;
            }
        }
        return true;
    }

    public static void generateValidBusinessHoursList() {
        trimValidBusinessHours();
        trimToEST();
    }

    public static void trimValidBusinessHours() {

        //The times here need to be filtered to the start times for the office which is
        // Start Time 8:AM
        StartTimesAddApp.forEach(localTime -> {
                    if (isValidStartBusinessHours(localTime)) {
                        StartTimesFiltered.add(localTime);
                    }
                }
        );
        // End   Time 10:PM
        EndTimesAddApp.forEach(localTime -> {
                    if (isValidEndBusinessHours(localTime)) {
                        EndTimesFiltered.add(localTime);
                    }
                }
        );
    }

    public static void trimToEST() {
        ObservableManager.StartTimesFiltered.forEach(localTime -> {
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);
            //Convert to a ZonedDateTime at the ZoneId
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            //Convert that to a ZonedDateTime at the target ZoneId
            ZonedDateTime estZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
            //Convert Back to LocalDateTime
            LocalDateTime localEstDateTime = estZonedDateTime.toLocalDateTime();
            //Just Need the Time the Date is unnecessary
            LocalTime localEstTime = localEstDateTime.toLocalTime();
            //Add This to the est list
            ObservableManager.StartTimesAddAppEst.add(localEstTime);
        });
        //Could perform a swap here with remove followed by an add operation, but I believe just adding to another list
        // is more efficient due to the shift operations , Unless I were to use streams. I have to bind to a list for
        // the comboBox anyway. So that's kind of my justification/reasoning behind this.
        ObservableManager.EndTimesFiltered.forEach(localTime -> {
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);
            //Convert to a ZonedDateTime at the ZoneId
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            //Convert that to a ZonedDateTime at the target ZoneId
            ZonedDateTime estZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
            //Convert Back to LocalDateTime
            LocalDateTime localEstDateTime = estZonedDateTime.toLocalDateTime();
            //Just Need the Time the Date is unnecessary
            LocalTime localEstTime = localEstDateTime.toLocalTime();
            ObservableManager.EndTimesAddAppEst.add(localEstTime);
        });
    }

    private static Boolean isValidStartBusinessHours(LocalTime time) {
        //Methods in the LocalDateTime class
        // A > B    /// A.isAfter(B)
        // A == B  ///  A.isEqual(B)
        // A < B   ///  A.isBefore(B)

        //To get A <= B
        // In LocalDateTimeMethods
        // (A.isBefore(B) || A.isEqual(B))

        //TO get A >= B
        //InLocalDateTimeMethods
        // A.isAfter(B) || A.isEqual(B)

        //Represent the Business Hours in Est
        return (time.isBefore(LocalTime.of(22, 0))
                && (time.isAfter(LocalTime.of(8, 0))))
                || (time.equals(LocalTime.of(8, 0)));
        //have to account for the edge cases 8AM and 10PM
    }

    private static Boolean isValidEndBusinessHours(LocalTime time) {
        //Represent the Business Hours in Est
        //have to account for the edge cases 8AM and 10PM
        return (time.isBefore(LocalTime.of(22, 0))
                && (time.isAfter(LocalTime.of(8, 0))))
                || (time.equals(LocalTime.of(22, 0)));
    }

    public static void recordAttempt(String username, boolean isAttemptValid) throws IOException {
        System.out.println("Login-File-Is-Keeping-Record");
        //First have to create the file is it does not already exist
        //Declarations
        File loginSheet = new File("login_activity.txt");

        try {
            System.out.println("Was a new file created: " + loginSheet.createNewFile());
            System.out.println("Does this Login-Sheet exist: " + loginSheet.exists());
        } catch (IOException e) {
            System.out.println("Problem creating Login Sheet.");
            throw new RuntimeException(e);
        }
        //Then we have to write to the file the record attempt
        try {
            FileWriter myWriter = new FileWriter("login_activity.txt", true);
            BufferedWriter myBufferedWriter = new BufferedWriter(myWriter);

            String baseMessage = "User [" + username + "] ";
            String baseAfterCheck = "in at " + LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MINUTES) + " UTC.";
            String check;
            String messageToWrite;
            //determine whether valid or invalid
            if (isAttemptValid) {
                check = "successfully logged ";
            } else {
                check = "gave invalid log-";
            }
            //Combine them and write
            messageToWrite = baseMessage + check + baseAfterCheck;
            myBufferedWriter.write(messageToWrite);
            myBufferedWriter.newLine();
            //Close the resources
            myBufferedWriter.close();
            myWriter.close();
            //logs
            System.out.println("Recording attempts in the Login Sheet.");
        } catch (IOException e) {
            System.out.println("An error occurred. Attempting to write to Login Sheet");
            e.printStackTrace();
        }
        System.out.println();
    }
}
