package main.DAO.models;

import java.time.Month;

public class MonthReport {
    private final Month month;
    private int appointmentCount;

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
