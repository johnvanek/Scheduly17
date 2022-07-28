package main.DAO.models;

/**
 * This class is a data model that represents a division ( a state or province ). The methods supplied for this class are only getters
 * as there are no features that require modification of a division.
 */
public class Division {

    /**
     * a integer representing the unique numeric id for a division
     */
    private final int divisionId;

    /**
     * a string representing the name of the division
     */
    private final String name;

    /**
     * a int representing the unique numeric id for a country
     */
    private final int countryId;

    /**
     * The constructor for a division
     *
     * @param divisionId an integer representing the unique numeric id for a division
     * @param name       a string representing the name of the division
     * @param countryId  an int representing the unique numeric id for a country
     */
    public Division(int divisionId, String name, int countryId) {
        this.divisionId = divisionId;
        this.name = name;
        this.countryId = countryId;
    }

    /**
     * Returns an int that is a representation the division id
     *
     * @return an integer representing the unique numeric id for a division
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Returns a string that is a representation of the division name
     *
     * @return a string representing the name of the division
     */
    public String getName() {
        return name;
    }

    /**
     * Returns an int that is a representation of the country id
     *
     * @return an int representing the unique numeric id for a country
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Overrides the toString() method such as when toString() is called on a division object
     * the name will appear in the output.
     *
     * @return a string that is representation of a division object
     */
    @Override
    public String toString() {
        return getName();
    }
}
