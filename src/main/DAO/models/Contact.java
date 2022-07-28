package main.DAO.models;

/**
 * This class is a data model that represents a contact. The methods supplied for this class are only getters
 * as there are no features that require modification of a Contact.
 */
public class Contact {
    /**
     * A distinct integer contact id. This is set by the database operation.
     */
    private final int contactID;
    /**
     * A string representation of the contact's name.
     */
    private final String contactName;
    /**
     * A string representation of the email of the contact.
     */
    private final String email;


    /**
     * @param contactID   A distinct integer contactID. This is set by the database operation.
     * @param contactName A string representation of the contact's Name.
     * @param email       A string representation of the email address of the contact.
     */
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Returns the contact id of the contact.
     *
     * @return int representing the contactID of the contact.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Returns the contact name that is a string that represent the contact's name.
     *
     * @return string representing the name of the contact.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Returns the email that is a string that represents the contact's email.
     *
     * @return string representing the email of the contact.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Overrides the toString() method of this contact class to output the contact: id and name when the toString()
     * method is called.
     *
     * @return string representing the contact object.
     */
    @Override
    public String toString() {
        return "ID[" + getContactID() + "] - " + getContactName();
    }
}
