package main.DAO.models;

public class FirstLevelDivision {
    private int divisionId;
    private String divisionName;
    private int countryId;

    public FirstLevelDivision(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public int getCountryId() {
        return countryId;
    }
}


