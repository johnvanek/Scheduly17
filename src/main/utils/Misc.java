package main.utils;

public class Misc {
    public static String makeFirstLetterUppercase(String lowerCaseString) {
        return lowerCaseString.substring(0, 1).toUpperCase() + lowerCaseString.substring(1);
    }
}
