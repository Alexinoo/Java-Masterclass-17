package nuts_and_bolts.part13_internationalization;

import javax.swing.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {

    public static void main(String[] args) {

        /*
         * Create a Resource bundle variable and set it to the result of calling a static () on this class getBundle()
         * getBundle() takes a single String - the baseName
         * Print the following
         *  - class name of the instance that comes back
         *  - base bundle name using getBaseBundleName()
         *  - keys
         * This code compiles but won't execute because we have not created a resource properties file
         *
         * Create a resources folder [ Project > New > Directory > resources ]
         * Create a new resource bundle in the resources folder [resources > New > ResourceBundle ]
         * Add Locales , comma separated e.g.  en_CA,fr_CA
         * Adds 3 files [ BasicText.properties ,BasicText_en_CA.properties,BasicText.fr_CA.properties ]
         * to the resources folder
         * Add resources folder to the class path [ resources > Mark Directory as > Resources Root]
         * Prints
         *  - java.util.PropertyResourceBundle
         *  - BasicText
         *  - []
         * PropertyResourceBundle - is a subclass of the abstract class ResourceBundle
         * Add key value pairs in BasicText.properties e.g. yes = yes  [# or ! for comments]
         * Prints the following - [no, world, yes, hello]
         *
         * Prints a formatted string using printf and use getString() which takes a String of the key specified
         * Note that the keys are case-sensitive and throws an error
         *
         * Installing plugin - [ Settings > Plugins > search Resource bundle > Click Install ]
         * Reload and 2 tabs appear at the bottom [Text / Resource Bundle ]
         *
         * Prints based on your default locale
         *
         * We can also specify the locale in the getBundle call
         * Let's test this code for diff locale by wrapping the code in a for loop,
         * Prints hello world! for all until we pass 2nd arg to getBundle()
         */
        for (Locale locale :List.of(Locale.US,Locale.CANADA_FRENCH,Locale.CANADA) ) {
            ResourceBundle rb = ResourceBundle.getBundle("BasicText",locale);
            //System.out.println(rb.getClass().getName());
            //System.out.println(rb.getBaseBundleName());
            //System.out.println(rb.keySet());

            System.out.printf("%s %s!%n",rb.getString("hello"),rb.getString("world"));

        }

        /*
         * Create UIComponents resource bundle
         * Add locale - fr
         * You can use period (.) with key names
         * You can also omit equal sign and add space/full-colon between key and value
         * Use tiny bit of java built-in User interface capabilities
         * JOptionPane - is GUI interface toolkit in the javax.swing package
         *  Call static showOptionDialog() on this toolkit which display a dialog window & takes 8 arg
         *      1st arg - parentComponent
         *      2nd arg - localized message - msg displayed to the user as part of the dialog
         *      3rd arg - first.title message - displayed as title for the dialog
         *      4th arg - JOptionPane.DEFAULT_OPTION - means dialog will have an OK button
         *      5th arg - JOptionPane.INFORMATION_MESSAGE - just used for info messages
         *      6th arg - icon
         *      7th arg - options - replace with an Object[] - contains the button label we want to use
         *                        - get it from the ui.getString("btn.ok")
         *      8th arg - initialValue
         */
        for (Locale locale :List.of(Locale.US,Locale.CANADA_FRENCH,Locale.CANADA) ) {

            ResourceBundle ui = ResourceBundle.getBundle("UIComponents",locale);
            String message = "%s%n".formatted(ui.getString("first.title"));
            JOptionPane.showOptionDialog(null,
                   message,
                    ui.getString("first.title"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{ui.getString("btn.ok")},
                    null);
        }

    }
}
