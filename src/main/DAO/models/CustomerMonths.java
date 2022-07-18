package main.DAO.models;

import java.time.Month;

public class CustomerMonths {
    private final Month month;
    private int totalAppointments;

    public CustomerMonths(Month month, int totalAppointments) {
        this.month = month;
        this.totalAppointments = totalAppointments;
    }
}
