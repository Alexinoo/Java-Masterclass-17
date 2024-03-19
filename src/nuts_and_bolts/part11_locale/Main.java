package nuts_and_bolts.part11_locale;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

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
          * For all English speaking locales
          */
         var dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

         for (Locale locale : List.of(Locale.getDefault(),Locale.US,en,enAU,enCA,enAU,Locale.UK,enIN,enNZ)){
             System.out.println(locale.getDisplayName()+ " = "+ LocalDateTime.now().format(dtf.withLocale(locale)));
         }

         /*
          * Print for non English locales
          * Create a date format instance using the .ofPattern()
          * Set LocalDate for May 5th - we get the same output
          * Set a for loop with a diff set of locale
          *
          * Java supports localization for the display methods on Locale
          * Prints the Locale in English since we have set default to English
          * Then prints in the language of a locale
          * Then full day of the week and the full month printed in the date and in the correct language for that locale
          *
          * Use printf, an overloaded version that takes locale as the first arg
          * Specifier is always %t then position specifier 1$
          * Capital A - gives full week day
          * Capital B - gives full week month text
          * used e - single-digit for day instead rather than d which gives 2-digits (e.g. May 5 / May 05)
          * Capital Y - gives full 4-digit year
          */
        System.out.println("_".repeat(50));
        DateTimeFormatter wdayMonth = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        LocalDate May5 = LocalDate.of(2020,5,5);
        for (Locale locale : List.of( Locale.CANADA,Locale.CANADA_FRENCH,Locale.FRANCE,Locale.GERMANY,Locale.TAIWAN,
                Locale.JAPAN,Locale.ITALY)){
            System.out.println(locale.getDisplayName() + " : "+
                    locale.getDisplayName(locale) + "=\n\t"+
                    May5.format(wdayMonth.withLocale(locale)));
            System.out.printf(locale,"\t%1$tA, %1$tB %1$td, %1$tY %n",May5);
        }

        /*
         * The method String.format also has an overloaded version that takes Locale
         * Need to use System.out.print() pass String.format(Locale locale,format specifier,LocalDate localdate)
         */
        System.out.println("_".repeat(50));
        System.out.print(String.format(Locale.KOREAN,"\t%1$tA, %1$tB %1$td, %1$tY %n",May5));

        /*
         * Java provides a NumberFormat class
         * Takes a locale to help us properly format numbers and currencies
         * See what a decimal number looks like in multiple places
         *  - A decimal point in some language is a period and in other's it's a comma
         *  - Numbers have different grouping separators, separating groupings of thousands,millions and so on
         *  - In English these are commas
         *  - In French these are spaces
         *  - In German these are periods
         * By default, a number format uses only 3 fractional places
         * NumberFormat lets us change that after creating the NumberFormat instance using setMaximumFractionDigits(int newValue)
         * This means NumberFormat is an immutable class
         *  - Get all 6-digits printed after decimal
         *
         * Do something similar with currency with getCurrencyInstance() for each locale
         * print each country method of displaying currency
         * used 3-digits after decimal point but rounded in almost all the locales
         *
         * Java also has a Currency class that can give us more info about a locale's currency using getInstance() with locale variable
         * Print some info about the currency
         *  - currency code -
         *  - display name for the currency
         *  - display name for each locale
         *
         */
        System.out.println("_".repeat(50));

        for (Locale locale : List.of( Locale.CANADA,Locale.CANADA_FRENCH,Locale.FRANCE,Locale.GERMANY,Locale.TAIWAN,
                Locale.JAPAN,Locale.ITALY)){
            System.out.println(locale.getDisplayName() + " : "+
                    locale.getDisplayName(locale) + "=\n\t"+
                    May5.format(wdayMonth.withLocale(locale)));

            NumberFormat decimalInfo = NumberFormat.getNumberInstance(locale);
            decimalInfo.setMaximumFractionDigits(6);
            System.out.println(decimalInfo.format(123456789.123456));

            NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
            Currency localCurrency = Currency.getInstance(locale);
            System.out.println(currency.format(555.5555) + " ["+
                        localCurrency.getCurrencyCode() + "] "+
                        localCurrency.getDisplayName(locale) + "/"+
                        localCurrency.getDisplayName()
                    );

        }

        /*
         * Switching gears
         * Keyboard input and the scanner class
         *
         * Create a BigDecimal - myLoan of 1000,50 and print it out
         * Gives error since the String passed to a BigDecimal constructor can't have any characters other than a decimal symbol and digits
         * But the Scanner class, will take the grouping symbol as input
         *
         * Create a scanner and request user to enter loan amount
         * use a method on scanner, that let's you read in a String and create a BigDecimal value of it called nextBigDecimal()
         * Replace new BigDecimal("1000,50"); with scanner.
         * If we proceed with entering exactly the same amount we had earlier :- 1,000.50
         * We don't get the error, since nextBIgDecimal will use the locale to test if this is a valid number
         * It also accepts, a Locale's group separator and decimal separator characters
         * We can change the locale used by the scanner by calling the useLocale() on scanner
         *
         * Let's try that by calling scanner.useLocale before getting the user input
         * If we run this and enter the same amount previously..1,000.50 - will get an exception
         * The scanner knows this input isn't appropriate for a BigDecimal number for Italy
         * Run it again and switch the characters used  in Italy format => 1.000,50
         * This works and we get "My Loan 1000.50" printed in Us locale
         *
         * We can also set up a number format using the Italy locale to print out for that locale
         */
        System.out.println("_".repeat(50));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the loan amount : ");
        scanner.useLocale(Locale.ITALY);

        BigDecimal myLoan = scanner.nextBigDecimal();
        System.out.println("My Loan (English locale) : " + myLoan);

        /*
         * We can also set up a number format using the Italy locale to print out for that locale
         */
        NumberFormat decimalInfo = NumberFormat.getNumberInstance(Locale.ITALY);
        System.out.println("My Loan (Italy locale) : " + decimalInfo.format(myLoan));

    }
}
