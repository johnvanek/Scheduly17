package main.DAO.models;

import java.time.LocalDateTime;

/**
 * This Class is a data model that represents an appointment
 */
public class Appointment {
    /**
     * A distinct integer ID. This is set by the database operation.
     */
    private final int appointmentId;
    /**
     * A String representing an appointment Title.
     */
    private String title;
    /**
     * A string containing a short description of the appointment.
     */
    private String description;
    /**
     * A string detailing the physical location of the appointment.
     */
    private String location;
    /**
     * A string detailing the type of the appointment for example a meeting.
     */
    private String type;
    /**
     * A LocalDateTime which represents the start time of an appointment.
     */
    private LocalDateTime startDateTime;
    /**
     * A LocalDateTime which represents the end time of an appointment.
     */
    private LocalDateTime endDateTime;
    /**
     * A integer representing customer's distinct ID.
     */
    private int customerId;
    /**
     * A integer representing a user's distinct ID.
     */
    private int userId;
    /**
     * A integer representing a contact's distinct ID.
     */
    private int contactId;


    /**
     * Constructor for an appointment data model
     *
     * @param appointmentId A distinct integer id. This is set by the database operation
     * @param title         A string representing an appointment Title
     * @param description   A string containing a short description of the appointment
     * @param location      A string detailing the physical location of the appointment
     * @param type          A string detailing the type of the appointment for example a meeting
     * @param startDateTime A localDateTime which represents the start time of an appointment
     * @param endDateTime   A localDateTime which represents the end time of an appointment
     * @param customerId    A integer representing customer's distinct ID
     * @param userId        A integer representing a user's distinct ID
     * @param contactId     A integer representing a contact's distinct ID
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Returns the appointment id int that is representative of the distinct appointment id
     *
     * @return int representing the appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }


    /**
     * Returns the title string that is representative of the title for an appointment
     *
     * @return string representing the title of the appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Assigns the appointment title to the value of the string parameter
     *
     * @param title string representing the title of the appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the string representation of a description of the appointment
     *
     * @return string representing the description of the appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * Assigns the Appointment description to the value of the string parameter
     *
     * @param description string representing the description of the appointment
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the string representation of the physical location of the appointment
     *
     * @return string representing the physical location of the appointment
     */
    public String getLocation() {
        return location;
    }


    /**
     * Assigns the appointment location to the value of the string parameter
     *
     * @param location string representing the physical location of the appointment
     */
    public void setLocation(String location) {
        this.location = location;
    }


    /**
     * Returns the string representation of the type of appointment
     *
     * @return string representing the type of appointment
     */
    public String getType() {
        return type;
    }

    /**
     * Assigns the Appointment type to the value of the string parameter
     *
     * @param type string representing the type of appointment
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns a LocalDateTime that is representative of an appointment start-time
     *
     * @return LocalDateTime representing the start-time of an appointment
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Assigns the LocalDateTime start-time to the value of the LocalDatetime parameter
     *
     * @param startDateTime LocalDateTime representing the start-time of an appointment
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }


    /**
     * Returns a LocalDateTime that is representative of an appointment end-time
     *
     * @return LocalDateTime representing the end-time of an appointment
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Assigns the LocalDateTime end-time to the value of the LocalDatetime parameter
     *
     * @param endDateTime LocalDateTime representing the end-time of an appointment
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }


    /**
     * Returns the customer id int that is representative of the distinct customer id
     *
     * @return int representing the customer id
     */
    public int getCustomerId() {
        return customerId;
    }


    /**
     * Assigns the customer id to the value of the int parameter
     *
     * @param customerId int representing the customerID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the user id int that is representative of the distinct user id
     *
     * @return int representing the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Assigns the user id to the value of the int parameter
     *
     * @param userId int representing the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the contact id int that is representative of the distinct contact id
     *
     * @return int representing the contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Assigns the contact id to the value of the int parameter.
     *
     * @param contactId int representing the contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
