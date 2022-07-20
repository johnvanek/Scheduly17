package main.DAO.models;

import java.time.Month;

public class MonthReport {
    private final Month month;
    private int appointmentCount;

    //TODO figure out how to filter this data by type. Watch the webinar about it.

    public MonthReport(Month month) {
        this.month = month;
    }

    public void incrementAppointmentCount() {
        appointmentCount++;
    }

    public Month getMonth() {
        return month;
    }

    public int getAppointmentCount() {
        return appointmentCount;
    }
}
