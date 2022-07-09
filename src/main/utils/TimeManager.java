package main.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import main.DAO.models.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static main.database.Query.executeQuery;
import static main.database.Query.getPreparedStatement;

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
        executeQuery(query);
        PreparedStatement ps = getPreparedStatement();
        ResultSet resultSet = ps.executeQuery();

        System.out.println("Comparing appointment times for potential customer overlap!!");
        Alert overlapAlert = new Alert(Alert.AlertType.ERROR, "This appointment you are trying to add overlaps" +
                "the customer with another existing appointment.", ButtonType.CLOSE);
        while (resultSet.next()) {
            Appointment currentAppointment = new Appointment(resultSet.getInt("appointment_ID"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("location"),
                    resultSet.getString("type"),
                    resultSet.getTimestamp("start").toLocalDateTime(), //Convert to the LocalTime
                    resultSet.getTimestamp("end").toLocalDateTime(),
                    resultSet.getInt("customer_ID"),
                    resultSet.getInt("user_ID"),
                    resultSet.getInt("Contact_ID"));

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

    //TODO implement the conversion method for est for the comboboxes for the add appointment screen.
}
