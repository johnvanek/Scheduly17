package main.DAO.models;

public class Country {

    private final int countryId;

    private final String countryName;

    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public int getCountryId() {
        return countryId;
    }

    @Override
    public String toString() {
        return getCountryName();
    }

    public String getCountryName() {
        return countryName;
    }
}
