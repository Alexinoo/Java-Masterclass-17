package nuts_and_bolts.part11_locale;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {

       // Locale.setDefault(Locale.FRENCH);
        Locale.setDefault(Locale.US);
        /*
         * Get default locale
         * Call static method on Locale - getDefault()
         * Gets the current value of the default locale for this instance of the JVM.
         * Prints en_US
         *
         * We can change the default locale by calling setDefault()
         * Done above
         * US is a static constant on the Locale class  change to AU
         */
        System.out.println("Default Locale = "+ Locale.getDefault());

        /*
         * Create Locale instances
         * For the majority of Locale, you will only need the language and country codes
         * At a minimum, you need a language code for the constructor
         * The Locale class does not have a no-args constructor
         * Creating a Locale using Locale(String language) constructor
         */
         Locale en = new Locale("en");

         /*
          * Creating a Locale using Locale(String language,String country) constructor
          */
         Locale enAU = new Locale("en","AU");
         Locale enCA = new Locale("en","CA");

        /*
         * Creating Locale using Locale.Builder
         * Builder is a nested static class
         * chain with setLanguage()
         * chain with setRegion()
         * chain a call to build() which returns a new Locale instance
         */

         Locale enIN = new Locale.Builder().setLanguage("en").setRegion("IN").build();
         Locale enNZ = new Locale.Builder().setLanguage("en").setRegion("NZ").build();

         /*
          * Use the above locale to print a date
          * set a date time format var
          * Loop through the locales above
          * Print a name for each by calling locale.getDisplayName() and get current date with the format specified for each locale
          *
          */
         var dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

         for (Locale locale : List.of(Locale.getDefault(),Locale.US,en,enAU,enCA,enAU,Locale.UK,enIN,enNZ)){
             System.out.println(locale.getDisplayName()+ " = "+ LocalDateTime.now().format(dtf.withLocale(locale)));
         }

    }
}
