package main.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import main.DAO.models.Appointment;
import main.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.stream.Collectors;

import static main.utils.ObservableManager.*;

public final class TimeManager {

    private TimeManager() {
    }

    public static boolean isInRangeWeekly(Appointment appointment) {
        //If the appointment is within a week of the Date going forward with time rounding till the end of the last day.
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime appStartDateTime = appointment.getStartDateTime();
        LocalDateTime sevenDaysForward = currentTime.plusDays(7);
        int endOfTheWorkWeek = sevenDaysForward.getDayOfYear();
        return ((currentTime.isBefore(appStartDateTime) && (currentTime.isBefore(sevenDaysForward) && appStartDateTime.isAfter(currentTime)) || currentTime.getDayOfYear() == endOfTheWorkWeek));
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
        Alert overlapAlert = new Alert(Alert.AlertType.ERROR, "This appointment you are trying to add overlaps" +
                "the customer with another existing appointment.", ButtonType.CLOSE);
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
                overlapAlert.show();
                return false;
            }
            //Condition 2 --|The appointment End is in between another existing meeting|--
            // (WantToAddEnd > DatabaseStart && WantToAddEnd <= DatabaseEnd)
            if ((appEnd.isAfter(databaseStart) && (appEnd.isBefore(databaseEnd) || appEnd.isEqual(databaseEnd)))) {
                overlapAlert.show();
                return false;
            }
            //Condition 3 --|Start and end consumes another smaller meeting completely|--
            if ((appStart.isBefore(databaseStart) || appStart.isEqual(databaseStart))
                    && (appEnd.isAfter(databaseEnd) || appEnd.isEqual(databaseEnd))) {
                overlapAlert.show();
                return false;
            }
        }
        return true;
    }

    public static void generateValidBusinessHoursList() {
        trimToEST();
        trimValidBusinessHours();
    }

    public static void trimValidBusinessHours() {

        //The times here need to be filtered to the start times for the office which is
        // Start Time 8:AM
        StartTimesAddAppEst.forEach(localTime -> {
                    if (isValidStartBusinessHours(localTime)) {
                        StartTimesFiltered.add(localTime);
                    }
                }
        );
        // End   Time 10:PM
        EndTimesAddAppEst.forEach(localTime -> {
                    if (isValidEndBusinessHours(localTime)) {
                        EndTimesFiltered.add(localTime);
                    }
                }
        );
    }

    public static void trimToEST() {
        ObservableManager.StartTimesAddApp.forEach(localTime -> {
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);
            System.out.println("The Local Date Time System: " + localDateTime);
            //Convert to a ZonedDateTime at the ZoneId
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            System.out.println("The Zoned Date Time System: " + zonedDateTime);
            //Convert that to a ZonedDateTime at the target ZoneId
            ZonedDateTime estZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
            System.out.println("The Zoned Date Time EST: " + estZonedDateTime);
            //Convert Back to LocalDateTime
            LocalDateTime localEstDateTime = estZonedDateTime.toLocalDateTime();
            System.out.println("The Local version of Est Date Time: " + localEstDateTime);
            //Just Need the Time the Date is unnecessary
            LocalTime localEstTime = localEstDateTime.toLocalTime();
            System.out.println("The local version of just EST Time: " + localEstTime);

            ObservableManager.StartTimesAddAppEst.add(localEstTime);
            //Add these into the new estList
        });
        //Could perform a swap here with remove followed by an add operation, but I believe just adding to another list
        // is more efficient due to the shift operations , Unless I were to use streams. I have to bind to a list for
        // the comboBox anyway. So that's kind of my justification/reasoning behind this.
        ObservableManager.EndTimesAddApp.forEach(localTime -> {
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);
            System.out.println("The Local Date Time System: " + localDateTime);
            //Convert to a ZonedDateTime at the ZoneId
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            System.out.println("The Zoned Date Time System: " + zonedDateTime);
            //Convert that to a ZonedDateTime at the target ZoneId
            ZonedDateTime estZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
            System.out.println("The Zoned Date Time EST: " + estZonedDateTime);
            //Convert Back to LocalDateTime
            LocalDateTime localEstDateTime = estZonedDateTime.toLocalDateTime();
            System.out.println("The Local version of Est Date Time: " + localEstDateTime);
            //Just Need the Time the Date is unnecessary
            LocalTime localEstTime = localEstDateTime.toLocalTime();
            System.out.println("The local version of just EST Time: " + localEstTime);

            ObservableManager.EndTimesAddAppEst.add(localEstTime);
            //Add these into the new estList
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
}
