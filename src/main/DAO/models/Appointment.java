package main.DAO.models;

import java.time.LocalDateTime;

/**
 * This Class is a data model that represents an appointment.
 */
public class Appointment {
    private final int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;
    private int userId;
    private int contactId;


    /**
     * Constructor for an appointment data model
     *
     * @param appointmentId A distinct integer ID.
     * @param title         A String representing a Title.
     * @param description   A string containing a short description of the appointment.
     * @param location      A string detailing the location of the appointment.
     * @param type          A string detailing the type of the appointment for example a meeting.
     * @param startDateTime A LocalDateTime which represents the start time of an appointment.
     * @param endDateTime   A LocalDateTime which represents the end time of an appointment.
     * @param customerId    A integer representing customer's distinct ID.
     * @param userId        A integer representing a user's distinct ID.
     * @param contactId     A integer representing a contact's distinct ID.
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
     * Returns the AppointmentID int that is representative of the distinct AppointmentID.
     *
     * @return int representing the appointmentID
     */
    public int getAppointmentId() {
        return appointmentId;
    }


    /**
     * Returns the Title string that is representative of the Title for an appointment.
     *
     * @return string representing the title of the appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Assigns the Appointment title to the value of the string parameter.
     *
     * @param title string representing the title of the appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the string representation of a description of the appointment.
     *
     * @return string representing the description of the appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Assigns the Appointment description to the value of the string parameter.
     *
     * @param description string representing the description of the appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the string representation of the physical location of the appointment.
     *
     * @return string representing the physical location of the appointment.
     */
    public String getLocation() {
        return location;
    }


    /**
     * Assigns the Appointment location to the value of the string parameter.
     *
     * @param location string representing the physical location of the appointment.
     */
    public void setLocation(String location) {
        this.location = location;
    }


    /**
     * Returns the string representation of the type of appointment.
     *
     * @return string representing the type of appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * Assigns the Appointment type to the value of the string parameter.
     *
     * @param type string representing the type of appointment.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns a LocalDateTime that is representative of an appointment start-time.
     *
     * @return LocalDateTime representing the start-time of an appointment.
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Assigns the LocalDateTime start-time to the value of the LocalDatetime parameter.
     *
     * @param startDateTime LocalDateTime representing the start-time of an appointment.
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }


    /**
     * Returns a LocalDateTime that is representative of an appointment end-time.
     *
     * @return LocalDateTime representing the end-time of an appointment.
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Assigns the LocalDateTime end-time to the value of the LocalDatetime parameter.
     *
     * @param endDateTime LocalDateTime representing the end-time of an appointment.
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }


    /**
     * Returns the CustomerID int that is representative of the distinct CustomerID.
     *
     * @return int representing the CustomerID
     */
    public int getCustomerId() {
        return customerId;
    }


    /**
     * Assigns the CustomerID to the value of the int parameter.
     *
     * @param customerId int representing the CustomerID.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the UserID int that is representative of the distinct UserID.
     *
     * @return int representing the UserID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Assigns the UserID to the value of the int parameter.
     *
     * @param userId int representing the UserID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the ContactID int that is representative of the distinct ContactID.
     *
     * @return int representing the ContactID.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Assigns the ContactID to the value of the int parameter.
     *
     * @param contactId int representing the ContactID.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
