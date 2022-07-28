package main.DAO.models;


/**
 * This class is a data model that represents a country. The methods supplied for this class are only getters
 * as there are no features that require modification of a country.
 */
public class Country {

    /**
     * A int representing a distinct id for the country.
     */
    private final int countryId;

    /**
     * A string representing the name of a country.
     */
    private final String countryName;

    /**
     * @param countryId A int representing a distinct ID for the country.
     * @param countryName A string representing the name of a country.
     */
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * Returns the countryID for the country.
     * @return int that represents a distinct id for the country.
     */
    public int getCountryId() {
        return countryId;
    }


    /**
     * Returns the country name represented as a string.
     * @return string that represents the country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Overrides the toString() method so that when toString() is called
     * the output is the name of the country.
     * @return a string that represents the Country Object.
     */
    @Override
    public String toString() {
        return getCountryName();
    }
}
