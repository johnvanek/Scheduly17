package main.utils;

/**
 * A utility class that serves to store miscellaneous useful functions.
 */
public final class Misc {

    /**
     * Private constructor so that this object cannot be instantiated
     */
    private Misc() {
    }

    /**
     * Given a string this method will uppercase the first character and send it back to the caller.
     *
     * @param lowerCaseString A string that is presumed to be lowercase
     * @return a string with the first char upper-cased
     */
    public static String makeFirstLetterUppercase(String lowerCaseString) {
        return lowerCaseString.substring(0, 1).toUpperCase() + lowerCaseString.substring(1);
    }
}
