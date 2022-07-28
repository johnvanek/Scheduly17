package main.DAO.models;

import java.time.Month;

/**
 * This class is a data model that represents the number of customer specific appointments that are in a month
 */
public class CustomerMonths {

    /**
     * unique int customer id represents a customer
     */
    private final int customerID;
    /**
     * enum month representing one of the twelve months
     */
    private final Month month;
    /**
     * total of all appointments in a month for a given customer id and a given month
     */
    private final int totalAppointments;

    /**
     * The constructor for the data model object customerMonths
     *
     * @param customerID        unique int customer id represents a customer
     * @param month             enum month representing one of the twelve months
     * @param totalAppointments total of all appointments in a month for a given customer id and a given month
     */
    public CustomerMonths(int customerID, Month month, int totalAppointments) {
        this.customerID = customerID;
        this.month = month;
        this.totalAppointments = totalAppointments;
    }
}
