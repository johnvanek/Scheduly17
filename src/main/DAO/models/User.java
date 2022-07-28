package main.DAO.models;

/**
 * This class is a data model that represents a user. The methods supplied for this class are only getters
 * as there are no features that require modification of a user.
 */
public class User {
    /**
     * an int that represents the unique numeric user id
     */
    private final int userID;
    /**
     * a string that represents the username
     */
    private final String userName;
    /**
     * a string that represents the user password
     */
    private final String password;

    /**
     * The constructor the user object
     * @param userID an int that represents the unique numeric user id
     * @param userName a string that represents the username
     * @param password a string that represents the user password
     */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Returns the unique user id represented as an int
     * @return an int that represents the unique numeric user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Returns the username of the user
     * @return  string that represents the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the password of the user
     * @return a string that represents the user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Overrides the toString() method such as when toString() is called
     * the output is a combination of the fields 'user id, username, and password'. This is only used for testing purposes.
     * @return a string representation of the user object
     */
    @Override
    public String toString() {
        return "User -[" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ']';
    }
}
