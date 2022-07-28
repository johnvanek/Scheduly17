package main.DAO.models;

/**
 * This Class is a data model that represents a Contact. The methods supplied for this Class are only getters
 * as there are no features that require modification of a Contact.
 */
public class Contact {
    /**
     * A distinct integer ContactID. This is set by the database operation.
     */
    private final int contactID;
    /**
     * A string representation of the Contact's Name.
     */
    private final String contactName;
    /**
     * A string representation of the Email of the Contact.
     */
    private final String email;


    /**
     * @param contactID   A distinct integer ContactID. This is set by the database operation.
     * @param contactName A string representation of the Contact's Name.
     * @param email       A string representation of the Email Address of the Contact.
     */
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    public int getContactID() {
        return contactID;
    }

    /**
     * Returns the ContactName that is a string that represent the Contact's name.
     *
     * @return string representing the Name of the Contact.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Returns the Email that is a string that represent the Contact's email.
     *
     * @return string representing the email of the Contact.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Overrides the toString() method of this Contact Class to output the Contact: ID and Name when the toString()
     * method is called.
     *
     * @return string representing the Contact Object.
     */
    @Override
    public String toString() {
        return "ID[" + getContactID() + "] - " + getContactName();
    }
}
