package main.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class ValidationChecker {
    public static Boolean isBusinessHoursValid(){
        //Todo implement the business hour validation
        //Date time is fine I will just have to cobble this information together from 2 fields.
        //Get the value here for the Date from the DatePicker and the other value from the combobox
        //Go and create those first then come back and test this out.

       //ZonedDateTime businessHoursStartEst = ZonedDateTime.from(LocalDateTime.of(8, 0));
       //System.out.println(businessHoursStartEst);
        System.out.println("Validation in progress...");
      return false;
    }

    public static Boolean isCustomerAvailable(){
        //Todo implement the Customer validation
        //scheduling an appointment outside of business hours defined as 8:00 a.m. to 10:00 p.m. EST, including weekends
        //•  scheduling overlapping appointments for customers
        //Call Time manager
        return false;
    }
}
