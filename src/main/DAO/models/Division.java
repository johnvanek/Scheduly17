package main.DAO.models;

public class Division {

    private final int divisionId;

    private final String name;

    private final int countryId;

    public Division(int divisionId, String name, int countryId) {
        this.divisionId = divisionId;
        this.name = name;
        this.countryId = countryId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getName() {
        return name;
    }

    public int getCountryId() {
        return countryId;
    }

    @Override
    public String toString() {
        return getName();
    }
}
