package nuts_and_bolts.part7_localdate;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Main {

    public static void main(String[] args) {

        /*
         * Get today's date
         * Prints 2024-03-17
         * Without formatting, this will be printed out in ISO8601 format which is a
         * 4-digit year , dash, a 2-digit month,dash and a 2-digit day
         */
        LocalDate today = LocalDate.now();
        System.out.println(today);

        /*
         * There are multiple ways to create LocalDates
         * Let's explore them py printing LocalDate for 5th May 2022
         * Also known as Cinco De Mayo
         * We call a static method on LocalDate .of and pass 3 params
         * 1st we pass year - 2022,2nd we pass month-5 and 3rd we pass day of the month-5
         * We get 2022-05-05
         *
         */
        LocalDate Five5 = LocalDate.of(2022,5,5);
        System.out.println(Five5);

        /*
         * Alternatively, we can pass month as enum value for May
         * We get the same date 2022-05-05
         */
        LocalDate May5th = LocalDate.of(2022, Month.MAY,5);
        System.out.println(May5th);

        /*
         * We can also get the date by passing the year and the day of the year
         * For May 5th - this is 125
         * Call a static method .ofYearDay and pass 2 parameters
         * 1st is an int -year and 2nd is also an int-dayOfYear
         * We get the same date 2022-05-05
         */
        LocalDate Day125 = LocalDate.ofYearDay(2022,125);
        System.out.println(Day125);

        /*
         * You can also create a LocalDate instance from a String using the static .parse()
         * The 1st version takes a single text literal in the same format as the default output
         * We get the same date 2022-05-05
         */
        LocalDate May5 = LocalDate.parse("2022-05-05");
        System.out.println(May5);

        /*
         * There is also an overloaded method of .parse() that uses a DateTimeFormatter
         * Let's you create a date using almost any String in any format
         * You might expect this class to have getters like getYear, getMonth and getDay that return
         * numeric values
         * getYear() returns int - 2022
         * getMonth() returns an enum constant - MAY
         * getMonthValue() returns integer value of the month (1-12) - 5
         * For  day, there is no getDay()
         */
        System.out.println(May5.getYear());
        System.out.println(May5.getMonth());
        System.out.println(May5.getMonthValue());

        /*
         * Instead need to figure out how you would want the day printed in context with 1 of three methods
         * getDayOfMonth() - 5,getDayOfWeek() - THURSDAY, getDayOfYear() - 125
         */
        System.out.println(May5.getDayOfMonth());
        System.out.println(May5.getDayOfWeek());
        System.out.println(May5.getDayOfYear());

        /*
         * How does Java store the day.?
         * It stores day, as the day of the month., the other values are calculated
         * In addition to getter methods, this class also implements TemporalAccessors get()
         * that takes a TemporalField parameter
         * i.e get(TemporalField field)
         * A TemporalField is an interface for a class that implements a date time field
         * Most of the time, you will use ChronosField enum that implements TemporalField to pick
         * the date time field you want to get
         * Let's get year,month and day again using just this one method
         */
        System.out.println("_".repeat(50));
        System.out.println(May5.get(ChronoField.YEAR));
        System.out.println(May5.get(ChronoField.MONTH_OF_YEAR));
        System.out.println(May5.get(ChronoField.DAY_OF_MONTH));
        System.out.println(May5.get(ChronoField.DAY_OF_YEAR));

        /*
         * Java does not give us any setters for like setYear, or setMonth like you would expect
         * But it does have methods with similar behaviour
         * These methods start with the prefix (with)
         * they would be setters, if this class were mutable, but basically you'll get a copy, with
         * one of the fields, year,month or day, changed
         * They return a new instance of LocalDate with the new value in the specified field
         * May5.withYear(2000) - gives us 2000-05-05
         * May5.withMonth(3) - gives us 2022-03-05
         * May5.withDayOfMonth(4) - gives us 2022-05-04
         * May5.withDayOfYear(126) - gives us 2022-05-06
         * However, our May5 remains unchanged - 2022-05-05
         */
        System.out.println("_".repeat(50));
        System.out.println(May5.withYear(2000));
        System.out.println(May5.withMonth(3));
        System.out.println(May5.withDayOfMonth(4));
        System.out.println(May5.withDayOfYear(126));
        System.out.println(May5);

        /*
         * Like a get(TemporalField field) , we also have with(TemporalField field,long newValue)
         * We can pass a Chronofield constant(field) and then the amount(newValue)
         * Let's call this on May5 and pass ChronoField.DAY_OF_YEAR as 1st arg
         * Pass 126 as the 2nd arg
         * We get 2022-05-06
         */
        System.out.println("_".repeat(50));
        System.out.println(May5.with(ChronoField.DAY_OF_YEAR,126));

        /*
         * In addition, to just setting fields, you can adjust them with some amount using the plus
         * or minus()
         * There are plus() methods for each field i.e.
         *  plusYears(long yearsToAdd)
         *  plusMonths(long monthsToAdd)
         *  plusDays(long daysToAdd)
         *  plusWeeks(long weeksToAdd)
         * Below, we have added 1 year to all instances but when we add 52 weeks,
         * the result will be 52 weeks to the day from Thur in Year 2022 to the same Thur in Year 2023
         * which isn't May 5th but May 4th
         */
        System.out.println("_".repeat(50));
        System.out.println(May5.plusYears(1)); //2023-05-05
        System.out.println(May5.plusMonths(12)); //2023-05-05
        System.out.println(May5.plusDays(365)); //2023-05-05
        System.out.println(May5.plusWeeks(52)); //2023-05-04

        /*
         * You can also use just the plus() or minus() or until()
         * Similar to the get(TemporalField field) & with(TemporalField field)
         * Both of these methods takes a TemporalUnit
         * The ChronoUnit enum implements the TemporalUnit interface and is used for this method
         * Takes 1st ar as amountToAdd and 2nd arg as the value of the ChronoUnit which can be
         *  DAYS,HOURS,CENTURIES,DECADES,MILLENNIA and so on
         * We get 2023-05-05 and 2021-05-05
         * Explore until() when we cover Duration class
         * There are also methods like minusDays,minusWeeks and so on which mirror the plus()
         */
        System.out.println("_".repeat(50));
        System.out.println(May5.plus(365, ChronoUnit.DAYS));
        System.out.println(May5.minus(365, ChronoUnit.DAYS));

        /*
         * A Temporal field rep a field of date-time such as Month-of-year,minute-of-hour
         * A Temporal unit is a measurement of time such as years,months,days,hours,min,seconds etc
         */

        /*
         * Let's look at comparison methods
         * isAfter() - checks if May5 is after the specified date - return boolean
         * isBefore() - checks if May5 is before the specified date - return boolean
         */
        System.out.println("_".repeat(50));
        System.out.println("May5 > today ? " + May5.isAfter(today));
        System.out.println("today < May5 ? " + May5.isBefore(today));

        /*
         * Use compareTo() to do something similar
         * Returns the comparator value, negative if less, positive if greater
         */
        System.out.println("_".repeat(50));
        System.out.println("May5 > today ? " + May5.compareTo(today));
        System.out.println("today < May5 ? " + today.compareTo(May5));

        /*
         * Use compareTo() with equal dates
         * We get 0 which is a Comparable result when 2 instances are equal
         */
        System.out.println("_".repeat(50));
        System.out.println("today = now ? "+today.compareTo(LocalDate.now()));

        /*
         * Alternatively, we can use equals()
         * Returns boolean if this is equal to the other date
         */
        System.out.println("_".repeat(50));
        System.out.println("today = now ? "+today.equals(LocalDate.now()));

        /*
         * There is another method on this class that is commonly used
         * isLeapYear() returns boolean
         * Returns true for 2024 & false for 2023
         */
        System.out.println("_".repeat(50));
        System.out.println(today.isLeapYear());
        System.out.println(May5.minusYears(1).isLeapYear());



    }
}
