package nuts_and_bolts.part8_localtime_localdatetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {

        /* LocalDate.datesUntil()
         * Returns a sequential ordered stream of dates.
         * Takes a local date which has to be greater than the date in the May5 variable
         * Call forEach on that stream printing each date
         *
         * We get 7 dates including 2022-05-05 but on including May 12th
         * Means start date is inclusive and end date is exclusive
         */
        LocalDate May5 = LocalDate.parse("2022-05-05");
        May5.datesUntil(May5.plusDays(7))
                .forEach(System.out::println);

        /*
         * There is also an overloaded version of datesUntil() that takes a second parameter, a Period
         * More on Period class to follow
         * Suppose we want to schedule something every week, starting with May 5, 2022 and ending with May 5, 2023
         * Generate a stream of dates for a full year from May 5 2022 to May 4 2023
         * But we really want dates that are in 7 increments
         * To achieve this, we need to another argument which is static () on Period instance called .ofDays(7)
         * Passed 7, so the period will be 7 days long
         */
        System.out.println("_".repeat(50));
        May5.datesUntil(May5.plusYears(1), Period.ofDays(7))
                .forEach(System.out::println);

        /*
         * LocalTime's functionality is a lot like LocalDate, just with different fields         *
         * Stores the hours as 0-23 inclusive
         * This means that 12pm or noon is stored as 12 but 1pm as 13, 2pm as 14 and so on
         * Start by getting LocalTime.now()
         * Prints 24-hour (hour:minute:seconds.nanoseconds)
         * Then a Period followed by nanoseconds if applicable
         * This class also has several static () for the factory () - .of()
         */
        System.out.println("_".repeat(50));
        LocalTime time = LocalTime.now();
        System.out.println(time);

        /*
         * .of(int hour , int min)
         * At a minimum, we can pass the hour and the min so my hour is 7 and 0 for min
         */
        System.out.println("_".repeat(50));
        LocalTime sevenAm = LocalTime.of(7,0);
        System.out.println(sevenAm);

        /*
         * .of(int hour , int min, int sec)
         * You can also pass a 4th arg which is nanoseconds but below is more sufficient
         */
        System.out.println("_".repeat(50));
        LocalTime sevenThirty = LocalTime.of(7,30,15);
        System.out.println(sevenThirty);

        /*
         * Like LocalDate, you can create a new LocalTime instance using the parse() with a String literal
         * The String literal can include hours and minutes or hours,mins and sec separated by semicolon
         * You have to stick to 2 digits for the hrs,min and sec
         * Nano sec are part of the seconds field but delimited by a period
         */
        System.out.println("_".repeat(50));
        LocalTime sevenPm = LocalTime.parse("19:00");
        LocalTime sevenThirtyPm = LocalTime.parse("19:30:15.1000");
        System.out.println(sevenPm);
        System.out.println(sevenThirtyPm);

        /*
         * Add just 1 digit for hrs/min/sec will give an error

        LocalTime SevenPm = LocalTime.parse("7:00");
        System.out.println(SevenPm);
        */

        /*
         * What happens if we change to 07:00
         * We get 07:00 which is not seven pm any longer
         * Test with get() and another ChronoField
         * Gives 07:00
         */
        LocalTime SevenPm = LocalTime.parse("07:00");
        System.out.println(SevenPm);

        /*
         * Chain with .get and AMPM_OF_DAY as chronofield
         * We get 0 and 1 respectively
         * get(ChronoField.AMPM_OF_DAY) - returns a zero if the time is before noon
         */
        System.out.println("_".repeat(50));
        System.out.println(SevenPm.get(ChronoField.AMPM_OF_DAY)); //0
        System.out.println(sevenThirtyPm.get(ChronoField.AMPM_OF_DAY)); //1

        /*
         * If we change it back to 19:00
         * We get 1
         */
        System.out.println("_".repeat(50));
        SevenPm = LocalTime.parse("19:00");
        System.out.println(SevenPm.get(ChronoField.AMPM_OF_DAY)); //1

        /*
         * There are also getHour(), getMin() and getSecond() and getNano() on LocalTime class
         * There is also a get(TemporalField field)
         * getHour() from sevenThirtyPm variable - prints 19
         * get(ChronoField.HOUR_OF_DAY) - prints 19
         */
        System.out.println("_".repeat(50));
        System.out.println(sevenThirtyPm.getHour());
        System.out.println(sevenThirtyPm.get(ChronoField.HOUR_OF_DAY));

        /*
         * We can't pass .get(ChronoField.YEAR) for both LocalTime and LocalDate classes
         * which is a temporal field which does not exist in that class
         * Same applies with plus() and minus() for Temporal Units
         */
        //System.out.println(sevenThirtyPm.get(ChronoField.YEAR)); - gives exception

        /* plus(long amountToAdd,TenporalUnit unit)
         * We can't add a day, but we can add 24 hrs
         * See examples below
         *
         */
        //System.out.println(sevenThirtyPm.plus(1, ChronoUnit.DAYS)); //throws an exception
        System.out.println(sevenThirtyPm.plus(24, ChronoUnit.HOURS)); //throws an exception

        /*
         * range()
         * Takes a Temporal field
         * See ranges below for different Chronofields
         */
        System.out.println("_".repeat(50));
        System.out.println(sevenThirtyPm.range(ChronoField.HOUR_OF_DAY)); //0-23
        System.out.println(sevenThirtyPm.range(ChronoField.MINUTE_OF_HOUR)); //0-59
        System.out.println(sevenThirtyPm.range(ChronoField.MINUTE_OF_DAY)); //0-1439
        System.out.println(sevenThirtyPm.range(ChronoField.SECOND_OF_MINUTE)); //0-59
        System.out.println(sevenThirtyPm.range(ChronoField.SECOND_OF_DAY)); //0-86339

        /*
         * LocalDateTime has 2 fields
         * LocalDate and LocalTime instances
         */

        /*
         * Create a LocalDateTime variable
         * Prints 2024-03-17T22:16:24.672158300
         * Prints date and time after T
         */
        System.out.println("_".repeat(50));
        LocalDateTime todayAndNow = LocalDateTime.now();
        System.out.println(todayAndNow);

        /*
         * .of(int year,int month,int dayOfMonth,int hour,int min)
         * Like LocalDate and LocalTime, there many overloaded methods of .of() factory ()
         * At a minimum, you need to pass year,month,dayOfMonth,hour and min
         * Use printf and print date and time with %t specifier
         * %tD - prints the date with / delimited 05/05/22
         * %tr - prints time 12:00:00 PM
         * Prints 05/05/22 12:00:00 PM
         */
        System.out.println("_".repeat(50));
        LocalDateTime May5Noon = LocalDateTime.of(2022,5,5,12,0);
        System.out.printf("%tD %tr %n",May5Noon,May5Noon);

        /*
         * Using position reference 1$
         * %1$tF - prints the date by default format
         * %1$tT - prints time 12:00:00 similar to default but excluding nano seconds
         * Only need to pass May5Noon once
         *
         * Prints 2022-05-05 12:00:00
         */
        System.out.printf("%1$tF %1$tT",May5Noon);

        /*
         * DateTimeFormatter - A class in java.time.format
         * This class has several static fields that are instances of this class
         * So you can leverage this for formatting
         */

        /* format(DateTimeFormatter.instance)
         * Print the result of calling format on todayAndNow
         * Takes a DateTimeFormatter instance
         *
         * Prints
         * 2024-W11-7 rep - year-week number - day of week
         */
        System.out.println();
        System.out.println("_".repeat(50));
        System.out.println(todayAndNow.format(DateTimeFormatter.ISO_WEEK_DATE));

        /*
         * Set up a DateTimeFormatter instance
         * Call an instance of .ofLocalizedDate()
         * Pass an enum constant on the FormatStyle.FULL
         *
         * Prints Thursday, May 5, 2022 in a more verbose way
         * Prints in consideration with locale, Will print above for US & KE and Thursday, 5 May 2022 for Australia
         */
        System.out.println("_".repeat(50));
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        System.out.println(May5Noon.format(dtf));

        /*
         * Print the date time now, using .ofLocalizedDateTime() passing the FormatStyle.MEDIUM
         *
         * Prints May 5, 2022, 12:00:00 PM
         */
        System.out.println("_".repeat(50));
        System.out.println(May5Noon.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        /*
         * The LocalDate Time class has all of LocalDate methods as well as LocalTime methods
         */

        /*
         * Calling plusHours(long hours)
         * Remember, we had an exception when we used it with a LocalTime-
         * We can use with LocalDateTime here and time shd remain the same but date shd be adjusted
         *
         * Prints May 6, 2022, 12:00:00 PM
         */
        LocalDateTime  May6Noon = May5Noon.plusHours(24);
        System.out.println(May6Noon.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

    }
}
