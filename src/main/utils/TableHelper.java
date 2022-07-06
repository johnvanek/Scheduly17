package main.utils;

//This class role is to figure out what tables are being referenced for the purposed of creating prepared statements.
public class TableHelper {
    //Todo add the logic for how TableHelper assigns the correct table could use a lambda here.
    private static final String[] AppointmentList = {
            "Appointment_ID",
            "Title",
            "Description",
            "Location",
            "Type",
            "Start",
            "End",
            "Customer_ID",
            "User_ID",
            "Contact_ID"
    };

    public static String[] getAppointmentList() {
        return AppointmentList;
    };
}
