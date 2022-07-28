package main.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A utility class that serves to assist getting the resource bundle for a language resource
 */
public final class LanguageManager {

    /**
     * A string representation of the language package name
     */
    private static final String resourceLocation = "Lang";
    /**
     * A locale representing the default locale set on the user's computer
     */
    private static final Locale locale = Locale.getDefault();
    /**
     * A resource bundle that represents the package containing the available languages key value pairs
     */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceLocation, locale);

    /**
     * Private constructor so that this object cannot be instantiated
     */
    private LanguageManager() {
    }

    /**
     * Returns the user's system set locale
     *
     * @return a locale representing the user's system set locale
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     * Returns a resource bundle that represents the set of key value pairs for the available languages
     *
     * @return a resource bundle representing a language's key value pairs
     */
    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}


