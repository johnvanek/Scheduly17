package main.utils;

import main.DAO.models.Appointment;
import main.database.Connection;
import main.database.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
        //if the start time is before the end time, and they are on the same day, I have restricted business hours using
        // The combo-boxes if they are on the same day they can only select business hours.
        // This disallows multiple day appointments.
        return (startDateTime.isBefore(endDateTime) && startDateTime.getDayOfYear() == endDateTime.getDayOfYear());
    }

    public static boolean isCustomerAvailable(int customerID, LocalDateTime appStart, LocalDateTime appEnd) throws SQLException {
        //3 conditions here to determine if there is an overlap

        //This comparison all need to be negated they are checking for the edge cases' existence
        // If any of these occur disregard the appointment
        //1 The start if this meeting is between another meeting
        // (WantToAddStart >= appointmentToCompareInDatabaseStart) && (WantToAddStart < appointmentToCompareInDatabaseEnd);
        //2 and End is in between another meeting
        // (WantToAddEnd > DatabaseStart && WantToAddEnd <= DatabaseEnd
        //3 Start and end consumes another smaller meeting completely.
        // WantToAddStart <= DatabaseStart && WantToAddEnd >= DatabaseEnd

        //Methods in the LocalDateTime class
        // A > B    /// A.isAfter(B)
        // A == B  ///  A.isEqual(B)
        // A < B   ///  A.isBefore(B)

        //To get A <= B
        // ( A < B || A == B )
        // In LocalDateTimeMethods
        // (A.isBefore(B) || A.isEqual(B))

        //Test case given an appointment in the database from 10 - 11am
        //10-1030 should overlap
        //10-11
        //1030 -11
        //9:30 to 11
        //9:30 - 10:30
        //9:30 - 11:30
        //Edge cases id there are Equal aka both start are at the same time that is a overlap
        //**** Formula ****
        //Assemble

        String query = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = Connection.getConnection().prepareStatement(query);
        ps.setInt(1,customerID);

        Query.executePreparedStatement(ps);
        ResultSet resultSet = Query.getResultSet();
        System.out.println("Comparing appointment times for potential customer overlap!!");
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
        }

        return true;
    }
}
