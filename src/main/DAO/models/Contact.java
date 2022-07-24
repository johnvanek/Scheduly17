package main.DAO.models;

public class Contact {
    private final int contactID;
    private final String contactName;
    private final String email;


    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return getContactName() + " -Id: " + getContactID();
    }
}
