package main.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import main.DAO.models.Appointment;
import main.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeManager {
    public static boolean isInRangeWeekly(Appointment appointment) {
        //If the appointment is within a week of the Date going forward with time rounding till the end of the last day.
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime appStartDateTime = appointment.getStartDateTime();
        LocalDateTime sevenDaysForward = currentTime.plusDays(7);
        int endOfTheWorkWeek = sevenDaysForward.getDayOfYear();
        return ((currentTime.isBefore(appStartDateTime) && (currentTime.isBefore(sevenDaysForward) && appStartDateTime.isAfter(currentTime)) || currentTime.getDayOfYear() == endOfTheWorkWeek));
    }

    public static boolean isInRangeMonthly(Appointment appointment) {
        // Till the end of the current month not a day more.
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime appStartDateTime = appointment.getStartDateTime();
        LocalDateTime monthForward = currentTime.plusMonths(1);
        //Need a way to figure out the days remaining in the end of the month
        return (currentTime.isBefore(appStartDateTime) && currentTime.isBefore(monthForward) && appStartDateTime.isAfter(currentTime) && currentTime.getMonth() == appStartDateTime.getMonth() && currentTime.getYear() == appStartDateTime.getYear());
    }

    public static ZonedDateTime convertToEst(LocalDateTime timeToCovertToEst) {
        //ZoneId systemZoneID = ZoneId.systemDefault();
        //Combines this date-time with a time-zone to create a ZonedDateTime. In this case EST
        return timeToCovertToEst.atZone(ZoneId.of("EST", ZoneId.SHORT_IDS));
    }

    public static boolean isInBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // The combo-boxes if they are on the same day they can only select business hours.
        // This disallows multiple day appointments.


        ZonedDateTime estStart = convertToEst(startDateTime);
        ZonedDateTime estEnd = convertToEst(endDateTime);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        System.out.println("The time right now in UTC is " + now);
        ZonedDateTime officeEightAmStart = now.with(LocalTime.of(8,0));
        System.out.println("The time that the office opens in Est -> Your Time is " + officeEightAmStart);
        ZonedDateTime officeTenPmClose = now.with(LocalTime.of(22,0));
        System.out.println("The time that the office closes in EST -> Your Time is " + officeTenPmClose);
        //estStart.isAfter()
        return true;
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
}
