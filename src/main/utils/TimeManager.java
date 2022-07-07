package main.utils;

import main.DAO.models.Appointment;

import java.time.LocalDateTime;

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
}
