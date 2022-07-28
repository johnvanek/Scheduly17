package main.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DAO.models.*;
import main.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.Month;
import java.util.stream.Collectors;

/**
 * A utility class that manages the observables lists that back the data for javafx
 */
public final class ObservableManager {
    //AppointmentObservable List's
    /**
     * An observable list that represents all appointments from the database
     */
    public static ObservableList<Appointment> AppointmentAllList = FXCollections.observableArrayList();
    /**
     * An observable list that represents all appointments that occur within the next week
     */
    public static ObservableList<Appointment> AppointmentWeeklyList = FXCollections.observableArrayList();
    /**
     * An observable list that represents all appointments that occur within the next month
     */
    public static ObservableList<Appointment> AppointmentMonthlyList = FXCollections.observableArrayList();
    // Appointment Add Screen Combo-Boxes
    /**
     * An observable list that represents local times for the start combo-box
     */
    public static ObservableList<LocalTime> StartTimesAddApp = FXCollections.observableArrayList();
    /**
     * An observable list that represents local times for the end time combo-box
     */
    public static ObservableList<LocalTime> EndTimesAddApp = FXCollections.observableArrayList();
    /**
     * An observable list that represents local times for the start combo-box converted to est
     */
    public static ObservableList<LocalTime> StartTimesAddAppEst = FXCollections.observableArrayList();
    /**
     * An observable list that represents local times for the end combo-box converted to est
     */
    public static ObservableList<LocalTime> EndTimesAddAppEst = FXCollections.observableArrayList();
    /**
     * An observable list that represents local times for the start combo-box converted to est and filtered to
     * available office hours
     */
    public static ObservableList<LocalTime> StartTimesFiltered = FXCollections.observableArrayList();
    /**
     * An observable list that represents local times for the end combo-box converted to est and filtered to
     * available office hours
     */
    public static ObservableList<LocalTime> EndTimesFiltered = FXCollections.observableArrayList();
    //Customer Observables
    /**
     * An observable list that represents all the customers
     */
    public static ObservableList<Customer> CustomerList = FXCollections.observableArrayList();
    /**
     * An observable list that represents all the countries
     */
    public static ObservableList<Country> CountryList = FXCollections.observableArrayList();
    /**
     * An observable list that represents all the provinces and states
     */
    public static ObservableList<Division> DivisionList = FXCollections.observableArrayList();

    //Report Observables

    /**
     * An observable list that represents the 12 months
     */
    public static ObservableList<Month> MonthList = FXCollections.observableArrayList();
    /**
     * An observable list that represents all available appointment types. Also includes a special type All Types!.
     */
    public static ObservableList<String> TypeList = FXCollections.observableArrayList();
    /**
     * An observable list that represents all contacts
     */
    public static ObservableList<Contact> ContactList = FXCollections.observableArrayList();
    /**
     * An observable list that represents all the appointments for a given contact
     */
    public static ObservableList<Appointment> ContactAppointmentList = FXCollections.observableArrayList();
    /**
     * An observable list that represents all appointments for a given customer
     */
    public static ObservableList<Appointment> CustomerAppointmentList = FXCollections.observableArrayList();
    /**
     * A User that represent the user that is currently logged in.
     */
    public static User currentlyLoggedInUser;

    /**
     * Contractor is private so that it cannot be instantiated.
     */
    private ObservableManager() {
    }

    /**
     * <p>Generates the observable typelist. Works by iterating over each appointment determining if they match the same type.</p>
     * <p><b>Lambda [4]</b> - Similar to lambda 2 and 3 use a foreach statement to iterate over all appointments
     * and passing in the current appointment evaluate the method {@link #determineIfInListAddType(Appointment)
     * determineIfInListAddType }  passing in the current appointment to that method as well.</p>
     */

    public static void generateTypeList() {
        //First clear the old list
        TypeList.clear();
        //Then for each appointment loop over and add each type if unique
        AppointmentAllList.forEach(app -> determineIfInListAddType(app));
        //Add this to the end To show the amount in each Month with regard to Type.
        TypeList.add("All Types!");
        //Finally Add one Type of All Types in case they wish to see the count for all appointments
    }

    /**
     * Generates the observable list representing the 12 months.
     */
    public static void generateMonthList() {
        MonthList.clear();
        MonthList.add(Month.JANUARY);
        MonthList.add(Month.FEBRUARY);
        MonthList.add(Month.MARCH);
        MonthList.add(Month.APRIL);
        MonthList.add(Month.MAY);
        MonthList.add(Month.JUNE);
        MonthList.add(Month.JULY);
        MonthList.add(Month.AUGUST);
        MonthList.add(Month.SEPTEMBER);
        MonthList.add(Month.OCTOBER);
        MonthList.add(Month.NOVEMBER);
        MonthList.add(Month.DECEMBER);
    }

    /**
     * Generates the observable list of contacts by performing a select database operation.
     */
    public static void generateContactListFromDatabase() {
        ContactList.clear();
        PreparedStatement ps;
        ResultSet rs;
        String query = "Select * From contacts";
        try {
            ps = Connection.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Contact currentContact = new Contact(
                            rs.getInt("Contact_ID"),
                            rs.getString("Contact_Name"),
                            rs.getString("Email")
                    );

                    //Add all the appointments here from the script to the data model.
                    ContactList.add(currentContact);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating the Observable List for ContactList");
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates the list of appointments for a given customer.
     *
     * @param customer a customer data model that represents a customer record from the database.
     */
    public static void generateCustomerAppointmentList(Customer customer) {
        CustomerAppointmentList.clear();
        for (Appointment app : AppointmentAllList) {
            if (app.getCustomerId() == customer.getCustomerId()) {
                CustomerAppointmentList.add(app);
            }
        }
    }

    /**
     * Generates the list of appointments for a given contact.
     *
     * @param selectedContact a contact that has been selected from a tablerow.
     */
    public static void generateContactAppointmentList(Contact selectedContact) {
        ContactAppointmentList.clear();
        for (Appointment app : AppointmentAllList) {
            if (app.getContactId() == selectedContact.getContactID()) {
                ContactAppointmentList.add(app);
            }
        }
    }

    /**
     * Given an appointment this method determines if the appointment is not a duplicate and if it
     * matches the type being compared the appointment gets added into the new list.
     *
     * @param app a appointment data model.
     */
    private static void determineIfInListAddType(Appointment app) {
        boolean isUnique = true;
        for (String type : TypeList) {
            if (app.getType().equals(type)) {
                isUnique = false;
                break;
            }
        }
        if (isUnique) {
            TypeList.add(app.getType());
        }
    }

    /**
     * Populates the information for the allAppointments observable list. This method should be called before attempting
     * to create other observables lists as most of the other lists are filtered versions of this list.
     */
    public static void populateDataAppointmentLists() {
        AppointmentAllList.clear(); // Clear out the old List before creating new ones
        AppointmentWeeklyList.clear();
        AppointmentMonthlyList.clear();
        PreparedStatement ps;
        ResultSet rs;
        String query = "Select * From appointments";
        try {
            ps = Connection.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Appointment currentAppointment = new Appointment(
                            rs.getInt("appointment_ID"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("location"),
                            rs.getString("type"),
                            rs.getTimestamp("start").toLocalDateTime(), //Convert to the LocalTime
                            rs.getTimestamp("end").toLocalDateTime(),
                            rs.getInt("customer_ID"),
                            rs.getInt("user_ID"),
                            rs.getInt("Contact_ID")
                    );

                    //Add all the appointments here from the script to the data model.
                    AppointmentAllList.add(currentAppointment);
                    //Only add the weekly ones for weekly
                    if (TimeManager.isInRangeWeekly(currentAppointment)) {
                        AppointmentWeeklyList.add(currentAppointment);
                    }
                    //Only add the Ones in the same month till the end of the month
                    if (TimeManager.isInRangeMonthly(currentAppointment)) {
                        AppointmentMonthlyList.add(currentAppointment);
                    }
                }
                //cleanup
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("Error creating the Observable List for appointments");
            throw new RuntimeException(e);
        }
    }

    /**
     * Populates the data that is to be used for the combo boxes regarding time.
     */
    public static void populateDataAppointmentComboBoxes() {
        //Clear out the old data from the combo-Boxes before adding new data.
        ObservableManager.StartTimesAddApp.clear();
        ObservableManager.EndTimesAddApp.clear();
        ObservableManager.StartTimesAddAppEst.clear();
        ObservableManager.EndTimesAddAppEst.clear();
        ObservableManager.StartTimesFiltered.clear();
        ObservableManager.EndTimesFiltered.clear();
        populateDataAppointmentStartComboBox();
        populateDataAppointmentEndComboBox();
    }

    /**
     * Populates the dat that is to be used for the country combo boxs in the customers scene.
     */
    public static void populateDataCustomerComboBoxes() {
        populateDataCountryList();
    }

    /**
     * Populates the observables list for the country list.
     */
    public static void populateDataCountryList() {

        CountryList.clear(); // Clear out the old List before creating new ones
        PreparedStatement ps;
        ResultSet rs;
        String query = "Select * From countries";
        try {
            ps = Connection.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Country currentCountry = new Country(
                            rs.getInt("Country_ID"),
                            rs.getString("Country")
                    );
                    //Add all the countries here from the script to the data model.
                    CountryList.add(currentCountry);
                }
                //cleanup
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("Error creating the Observable List for Countries");
            throw new RuntimeException(e);
        }
    }

    /**
     * Populates the observable list for the divisions ( states and provinces )
     */
    public static void populateDataDivisionList() {
        DivisionList.clear(); // Clear out the old List before creating new one
        PreparedStatement ps;
        ResultSet rs;
        String query = "Select * From first_level_divisions";
        try {
            ps = Connection.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Division currentDivision = new Division(
                            rs.getInt("Division_ID"),
                            rs.getString("Division"),
                            rs.getInt("COUNTRY_ID")
                    );
                    DivisionList.add(currentDivision);
                }
                //cleanup
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("Error creating the Observable List for Divisions");
            throw new RuntimeException(e);
        }
    }


    /**
     * <p>Given the list of divisions and integer representing a country, the method returns a list of divisions that
     * match the country.</p>
     *
     * <p><b>Lamba [5]</b> - Works taking the division list and converting it to a stream, then filtering
     * appointments using <b>( division -> division.getCountryId() == countryID )</b> to satisfy the predicate
     * for filter. Followed by collectors.toCollection to put these elements back into an observable array list to be
     * returned.
     * </p>
     *
     * @param divisionList The list of divisions ( provinces and states ).
     * @param countryID    The unique country ID representing a country.
     * @return a list of divisions that match the given country code.
     */
    public static ObservableList<Division> searchByCountryCode(ObservableList<Division> divisionList, int countryID) {
        return divisionList
                .stream()
                .filter(division -> division.getCountryId() == countryID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * Populates the data for the observable list of customers.
     */
    public static void populateDataCustomerList() {
        //Clear out the old list before creating a new one
        CustomerList.clear();

        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM customers";
        try {
            ps = Connection.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Customer currentCustomer = new Customer(
                            rs.getInt("Customer_ID"),
                            rs.getString("Customer_Name"),
                            rs.getString("Address"),
                            rs.getString("Postal_Code"),
                            rs.getString("Phone"),
                            rs.getInt("Division_ID")
                    );
                    //Add all the Customers
                    CustomerList.add(currentCustomer);
                }
                //cleanup
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("Error creating the Customer-List");
            throw new RuntimeException(e);
        }
    }

    /**
     * Populates the data for combo boxs for the appointments scene start-time combo-boxs.
     */
    public static void populateDataAppointmentStartComboBox() {
        StartTimesAddApp.addAll(
                //These are all the available times In local.
                LocalTime.of(0, 0),
                LocalTime.of(0, 15),
                LocalTime.of(0, 30),
                LocalTime.of(0, 45),
                LocalTime.of(1, 0),
                LocalTime.of(1, 15),
                LocalTime.of(1, 30),
                LocalTime.of(1, 45),
                LocalTime.of(2, 0),
                LocalTime.of(2, 15),
                LocalTime.of(2, 30),
                LocalTime.of(2, 45),
                LocalTime.of(3, 0),
                LocalTime.of(3, 15),
                LocalTime.of(3, 30),
                LocalTime.of(3, 45),
                LocalTime.of(4, 0),
                LocalTime.of(4, 15),
                LocalTime.of(4, 30),
                LocalTime.of(4, 45),
                LocalTime.of(5, 0),
                LocalTime.of(5, 15),
                LocalTime.of(5, 30),
                LocalTime.of(5, 45),
                LocalTime.of(6, 0),
                LocalTime.of(6, 15),
                LocalTime.of(6, 30),
                LocalTime.of(6, 45),
                LocalTime.of(7, 0),
                LocalTime.of(7, 15),
                LocalTime.of(7, 30),
                LocalTime.of(7, 45),
                LocalTime.of(8, 0),
                LocalTime.of(8, 15),
                LocalTime.of(8, 30),
                LocalTime.of(8, 45),
                LocalTime.of(9, 0),
                LocalTime.of(9, 15),
                LocalTime.of(9, 30),
                LocalTime.of(9, 45),
                LocalTime.of(10, 0),
                LocalTime.of(10, 15),
                LocalTime.of(10, 30),
                LocalTime.of(10, 45),
                LocalTime.of(11, 0),
                LocalTime.of(11, 15),
                LocalTime.of(11, 30),
                LocalTime.of(11, 45),
                LocalTime.of(12, 0),
                LocalTime.of(12, 15),
                LocalTime.of(12, 30),
                LocalTime.of(12, 45),
                LocalTime.of(13, 0),
                LocalTime.of(13, 15),
                LocalTime.of(13, 30),
                LocalTime.of(13, 45),
                LocalTime.of(14, 0),
                LocalTime.of(14, 15),
                LocalTime.of(14, 30),
                LocalTime.of(14, 45),
                LocalTime.of(15, 0),
                LocalTime.of(15, 15),
                LocalTime.of(15, 30),
                LocalTime.of(15, 45),
                LocalTime.of(16, 0),
                LocalTime.of(16, 15),
                LocalTime.of(16, 30),
                LocalTime.of(16, 45),
                LocalTime.of(17, 0),
                LocalTime.of(17, 15),
                LocalTime.of(17, 30),
                LocalTime.of(17, 45),
                LocalTime.of(18, 0),
                LocalTime.of(18, 15),
                LocalTime.of(18, 30),
                LocalTime.of(18, 45),
                LocalTime.of(19, 0),
                LocalTime.of(19, 15),
                LocalTime.of(19, 30),
                LocalTime.of(19, 45),
                LocalTime.of(20, 0),
                LocalTime.of(20, 15),
                LocalTime.of(20, 30),
                LocalTime.of(20, 45),
                LocalTime.of(21, 0),
                LocalTime.of(21, 15),
                LocalTime.of(21, 30),
                LocalTime.of(21, 45),
                LocalTime.of(22, 0),
                LocalTime.of(22, 15),
                LocalTime.of(22, 30),
                LocalTime.of(22, 45),
                LocalTime.of(23, 0),
                LocalTime.of(23, 15),
                LocalTime.of(23, 30),
                LocalTime.of(23, 45)
        );
    }

    /**
     * Populates the data for combo boxs for the appointments scene end-time combo-boxs.
     */
    public static void populateDataAppointmentEndComboBox() {
        EndTimesAddApp.addAll(
                LocalTime.of(0, 15),
                LocalTime.of(0, 30),
                LocalTime.of(0, 45),
                LocalTime.of(1, 0),
                LocalTime.of(1, 15),
                LocalTime.of(1, 30),
                LocalTime.of(1, 45),
                LocalTime.of(2, 0),
                LocalTime.of(2, 15),
                LocalTime.of(2, 30),
                LocalTime.of(2, 45),
                LocalTime.of(3, 0),
                LocalTime.of(3, 15),
                LocalTime.of(3, 30),
                LocalTime.of(3, 45),
                LocalTime.of(4, 0),
                LocalTime.of(4, 15),
                LocalTime.of(4, 30),
                LocalTime.of(4, 45),
                LocalTime.of(5, 0),
                LocalTime.of(5, 15),
                LocalTime.of(5, 30),
                LocalTime.of(5, 45),
                LocalTime.of(6, 0),
                LocalTime.of(6, 15),
                LocalTime.of(6, 30),
                LocalTime.of(6, 45),
                LocalTime.of(7, 0),
                LocalTime.of(7, 15),
                LocalTime.of(7, 30),
                LocalTime.of(7, 45),
                LocalTime.of(8, 0),
                LocalTime.of(8, 15),
                LocalTime.of(8, 30),
                LocalTime.of(8, 45),
                LocalTime.of(9, 0),
                LocalTime.of(9, 15),
                LocalTime.of(9, 30),
                LocalTime.of(9, 45),
                LocalTime.of(10, 0),
                LocalTime.of(10, 15),
                LocalTime.of(10, 30),
                LocalTime.of(10, 45),
                LocalTime.of(11, 0),
                LocalTime.of(11, 15),
                LocalTime.of(11, 30),
                LocalTime.of(11, 45),
                LocalTime.of(12, 0),
                LocalTime.of(12, 15),
                LocalTime.of(12, 30),
                LocalTime.of(12, 45),
                LocalTime.of(13, 0),
                LocalTime.of(13, 15),
                LocalTime.of(13, 30),
                LocalTime.of(13, 45),
                LocalTime.of(14, 0),
                LocalTime.of(14, 15),
                LocalTime.of(14, 30),
                LocalTime.of(14, 45),
                LocalTime.of(15, 0),
                LocalTime.of(15, 15),
                LocalTime.of(15, 30),
                LocalTime.of(15, 45),
                LocalTime.of(16, 0),
                LocalTime.of(16, 15),
                LocalTime.of(16, 30),
                LocalTime.of(16, 45),
                LocalTime.of(17, 0),
                LocalTime.of(17, 15),
                LocalTime.of(17, 30),
                LocalTime.of(17, 45),
                LocalTime.of(18, 0),
                LocalTime.of(18, 15),
                LocalTime.of(18, 30),
                LocalTime.of(18, 45),
                LocalTime.of(19, 0),
                LocalTime.of(19, 15),
                LocalTime.of(19, 30),
                LocalTime.of(19, 45),
                LocalTime.of(20, 0),
                LocalTime.of(20, 15),
                LocalTime.of(20, 30),
                LocalTime.of(20, 45),
                LocalTime.of(21, 0),
                LocalTime.of(21, 15),
                LocalTime.of(21, 30),
                LocalTime.of(21, 45),
                LocalTime.of(22, 0),
                LocalTime.of(22, 15),
                LocalTime.of(22, 30),
                LocalTime.of(22, 45),
                LocalTime.of(23, 0),
                LocalTime.of(23, 15),
                LocalTime.of(23, 30),
                LocalTime.of(23, 45),
                LocalTime.of(0, 0)
        );
    }
}
