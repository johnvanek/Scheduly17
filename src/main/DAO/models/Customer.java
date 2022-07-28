package main.DAO.models;

/**
 * This Class is a data model that represents a customer
 */
public class Customer {
    /**
     * A distinct integer id for the customer. This is set by the database operation.
     */
    private final int customerId;
    /**
     * The unique id representing the province that the customer lives.
     */
    private final int divisionId;
    /**
     * The name of the customer.
     */
    private final String customerName;
    /**
     * The address of the customer
     */
    private final String address;
    /**
     * The postalCode of the customer represented as a string
     */
    private final String postalCode;
    /**
     * The phone number of the customer represented as a string
     */
    private final String phoneNumber;

    /**
     * The constructor for the Customer object
     *
     * @param customerId   A distinct integer id for the customer. This is set by the database operation.
     * @param customerName The name of the customer
     * @param address      The address of the customer represented as a string
     * @param postalCode   The postalCode of the customer represented as a string
     * @param phoneNumber  The phone number of the customer represented as a string
     * @param divisionId   The unique id representing the province that the customer lives
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    /**
     * Returns an integer that represents a customer's unique id
     *
     * @return an integer representing the unique customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Returns a string representing the customer's name
     *
     * @return a string representation of the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Returns a string representing the customer's address
     *
     * @return a string representation of the customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns a string representing the customer's postal code
     *
     * @return a string representation of the customer postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Returns a string representing the customer's phone number
     *
     * @return a string representation of the customer phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns a unique int that represents the province or state that a customer lives in
     *
     * @return an int a unique id that represents the province a customer lives in
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Overrides the toString() method so that when called the output consists of the customer id and the customer name
     *
     * @return a string that represents the customer object
     */
    @Override
    public String toString() {
        return "ID[" + getCustomerId() + "] - " + getCustomerName();
    }
}
