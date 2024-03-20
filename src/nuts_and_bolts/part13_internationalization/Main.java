package nuts_and_bolts.part13_internationalization;

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
         * Add key value pairs in BasicText.properties e.g. yes = yes  [# for comment]
         * Prints the following - [no, world, yes, hello]
         *
         * Prints a formatted string using printf and use getString() which takes a String of the key specified
         * Note that the keys are case-sensitive and throws an error
         */
        ResourceBundle rb = ResourceBundle.getBundle("BasicText");
        System.out.println(rb.getClass().getName());
        System.out.println(rb.getBaseBundleName());
        System.out.println(rb.keySet());

        System.out.printf("%s %s!%n",rb.getString("hello"),rb.getString("world"));
    }
}
