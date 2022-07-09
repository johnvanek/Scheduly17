package main.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static final String resourceLocation = "Lang";
    private static final Locale locale = Locale.getDefault();

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceLocation, locale);
    public static Locale getLocale(){
        return locale;
    }

    public static ResourceBundle getResourceBundle(){
        return resourceBundle;
    }
}


