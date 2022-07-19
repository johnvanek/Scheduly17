package main.DAO.models;

import java.time.Month;

public class CustomerMonths {

    private final int customerID;
    private final Month month;
    private int totalAppointments;

    public CustomerMonths(int customerID, Month month, int totalAppointments) {
        this.customerID = customerID;
        this.month = month;
        this.totalAppointments = totalAppointments;
    }
}
