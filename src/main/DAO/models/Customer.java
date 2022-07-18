package main.DAO.models;

public class Customer {
    private final int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private final int divisionId;

    @Override
    public String toString() {
        return "ID[" + getCustomerId() + "] - " + getCustomerName();
    }

    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId) {
        //Only the fields customerId and division are without their own setter.
        //Those fields are set in the constructor and then that is it.
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDivisionId() {
        return divisionId;
    }
}
