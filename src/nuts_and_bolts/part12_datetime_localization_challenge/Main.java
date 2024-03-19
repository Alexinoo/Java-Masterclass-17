package nuts_and_bolts.part12_datetime_localization_challenge;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.zone.ZoneRules;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;
import static java.time.format.DateTimeFormatter.*;

/*
 * Schedule a meeting for people in different areas of the world
 * One from the East Coast of US and another in Sydney Australia
 * Print possible hrs in the upcoming 10-day range - ignore current day when these 2 employees could
 * potentially meet together
 * Print the date and time of each possible hr, for both zones, using the employee's locale
 */

public class Main {

    /*
     * Create an employee record that links our employee with both a local and time zone
     * Keeps data associated with each employee nicely
     * Set up custom constructors to create Employees with either a locale string or a local obj
     * Pass a string for the zone id rather than have the client construct a ZoneId obj
     * call canonical constructor
     *
     */
    private record Employee(String name, Locale locale , ZoneId zone){

        public Employee(String name, String locale, String zone) {
            this(name,Locale.forLanguageTag(locale),ZoneId.of(zone));
        }

        public Employee(String name, Locale locale, String zone) {
            this(name,locale,ZoneId.of(zone));
        }

        /*
         * Add getDateInfo() to print date in this employee locale and time zone
         * Make it package-private since nobody outside Main class needs access to it
         * Return a formatted string of name,zone and a date - formatted as per the dtf localized
         * by the employee locale
         */
        String getDateInfo(ZonedDateTime zdt, DateTimeFormatter dtf){
            return "%s [%s] : %s".formatted(name,zone,zdt.format(dtf.localizedBy(locale)));
        }
    }

    public static void main(String[] args) {

        /*
         * Create 1 employee from East Coast of Us - New York
         * Another one from Sydney Australia
         */
        Employee jane = new Employee("Jane",Locale.US,"America/New_York");
        Employee joe = new Employee("Joe","en-US","Australia/Sydney");

        /*
         * Find out how many hrs diff between these 2 employees
         * Set up 2 local variables for the zone rules ofr each of these 2 employees
         */
        ZoneRules joesRules = joe.zone.getRules();
        ZoneRules janesRules = jane.zone.getRules();

        System.out.println(jane + " "+ janesRules);
        System.out.println(joe + " "+ joesRules);

        /*
         * Test whether the above is true by using between method on Durations class
         * Set up an instance of ZonedDateTime
         */
        ZonedDateTime janeNow = ZonedDateTime.now(jane.zone);
        ZonedDateTime joeNow = ZonedDateTime.of(janeNow.toLocalDateTime(),joe.zone);

        long hrsBetween = Duration.between(joeNow,janeNow).toHours();
        long minutesBetween = Duration.between(joeNow,janeNow).toMinutesPart();
        System.out.println("Joe is "+Math.abs(hrsBetween)+ " hours "+
                            Math.abs(minutesBetween) + " minutes "+
                ((hrsBetween < 0) ? "behind":"ahead"));

        /*
         * Suppose we want more info about which employee is in daylight savings rules & the rules for that
         * pass joeNow.toInstant to isDaylightSavings() and getDaylightSavings()
         */
        System.out.println("Joe in daylight savings? "+
                joesRules.isDaylightSavings(joeNow.toInstant()) + " "+
                joesRules.getDaylightSavings(joeNow.toInstant()) + ": "+
                joeNow.format(ofPattern("zzzz z")));

        System.out.println("Jane in daylight savings? "+
                janesRules.isDaylightSavings(janeNow.toInstant()) + " "+
                janesRules.getDaylightSavings(janeNow.toInstant()) + ": "+
                janeNow.format(ofPattern("zzzz z")));

        /*
         * Identify the hrs that these 2 can meet
         * Check 10 days ahead and call the schedule method
         * Add a formatter
         * Loop the resulting keys
         */
        int days = 10;
        var map = schedule(joe,jane,days);
        DateTimeFormatter dtf = ofLocalizedDateTime(FormatStyle.FULL,FormatStyle.SHORT);
        for (LocalDate ldt : map.keySet()){
            System.out.println(ldt.format(ofLocalizedDate(FormatStyle.FULL)));
            for (ZonedDateTime zdt : map.get(ldt)){
                System.out.println("\t "+
                    jane.getDateInfo(zdt,dtf) +" <---> "+
                    joe.getDateInfo(zdt.withZoneSameInstant(joe.zone()),dtf));
            }
        }
    }
        /*
         * Set schedule() that returns a Map and takes the 2 employees and the no of days we will use to
         * search ahead for matches
         * Create the rules to select which hrs and days to match on as a predicate because we are going to use
         * streams to create these hourly slots and we will use it to filter out hrs that don't work for each employee
         *
         * We assume the rules are the same for both employees
         *  - Day of the week can't be SATURDAY or SUNDAY
         *  - Hour can't be less than 7 am or more than 8pm or (21) in 24hr
         * Set starting date as current LocalDate.now() but add 2 days to that
         */
    private static Map<LocalDate, List<ZonedDateTime>> schedule(Employee first,
                                                                Employee second,
                                                                int days){
        Predicate<ZonedDateTime> rules = (zdt)->
                zdt.getDayOfWeek() != DayOfWeek.SATURDAY &&
                zdt.getDayOfWeek() != DayOfWeek.SUNDAY &&
                zdt.getHour() >= 7 && zdt.getHour() < 21;

        LocalDate startingDate = LocalDate.now().plusDays(2);

        return startingDate.datesUntil(startingDate.plusDays(days + 1))
                .map(dt -> dt.atStartOfDay(first.zone()))
                .flatMap(dt -> IntStream.range(0,24).mapToObj(dt::withHour))
                .filter(rules)
                .map(dtz -> dtz.withZoneSameInstant(second.zone()))
                .filter(rules)
                .collect(
                        Collectors.groupingBy(ZonedDateTime::toLocalDate,
                                TreeMap::new ,
                                Collectors.toList())
                );

    }
}
