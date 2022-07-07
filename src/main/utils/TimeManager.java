package main.utils;

import main.DAO.models.Appointment;

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
        return (startDateTime.isBefore(endDateTime) && startDateTime.getDayOfYear() == endDateTime.getDayOfYear());
    }

    public static boolean isCustomerAvailable(int custID, LocalDateTime start, LocalDateTime end) {
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

        //These are the steps to calculate if there is an overlap
        //1. Step the start and end times in combo boxes already done.
        //get the data
        //2. (current) Loop over all the records in the database
            //Could just loop over the observable list of existing appointments
            // Then if that is good at is to the database, and it will repopulate when to
            // Initialize is called.
        try {
            ObservableManager.createAppointmentList();
        } catch (SQLException e) {
            System.out.println("Problem Creating ObservableList's");
            throw new RuntimeException(e);
        }
        // Only filter for the one with the same customer id.
        // In the case of modify ignore the appointment that has the same id as the one being edited.
        //Then check for the overlap.
    return true;
    }
}
