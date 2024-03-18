package nuts_and_bolts.part9_timezones;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

public class Main {
    public static void main(String[] args) {

        System.setProperty("user.timezone","America/Los_Angeles");

        /* We can also change the above to UTC */
       // System.setProperty("user.timezone","UTC");

        /* We can also change from UTC to GMT as well */
      //  System.setProperty("user.timezone","GMT");

        /*
         * ZoneId class comes with a lot of helpful methods
         * ................................................
         * systemDefault()
         * Returns the system default time-zone
         * Prints Africa/Nairobi
         */
        System.out.println(ZoneId.systemDefault()); //Africa/Nairobi

        /*
         * Java comes with a list of supported zones
         * getAvailableZoneIds()
         * Returns a set of available zone ids as strings
         * Use a stream to print out these time zones
         */
        System.out.println("_".repeat(50));
        ZoneId.getAvailableZoneIds()
                .stream()
                .sorted()
                .forEach(System.out::println);

        /*
         * Filter only time zones in Africa
         */
        System.out.println("_".repeat(50));
        ZoneId.getAvailableZoneIds()
                .stream()
                .filter(s -> s.startsWith("Africa"))
                .sorted()
                .forEach(System.out::println);

        /*
         * size()
         * Returns the size of all available zoneIDs - 603
         */
        System.out.println("_".repeat(50));
        System.out.println("Number of Time Zone's : "+ZoneId.getAvailableZoneIds().size());

        /*
         * Create an instance of a zone using .of() factory method
         * Do with a stream pipeline using mpa intermediate operation
         * The toString() on ZoneIds simply prints out the id
         */
        System.out.println("_".repeat(50));
        ZoneId.getAvailableZoneIds()
                .stream()
                .filter(s -> s.startsWith("Africa"))
                .sorted()
                .map(ZoneId::of)
                .forEach(System.out::println);

        /*
         * ZoneId class has other methods and fields
         * Let's change System.out::println to a lambda expression
         * Print out the id with getId()
         * Also print out rules with getRules() - rules are part of the ZoneId class
         */

        System.out.println("_".repeat(50));
        ZoneId.getAvailableZoneIds()
                .stream()
                .filter(s -> s.startsWith("Africa"))
                .sorted()
                .map(ZoneId::of)
                .forEach(z -> System.out.println(z.getId() + " : "+ z.getRules()));

        /*
         * get available zoneIDs using getAvailableZoneIds() on ZoneId class- returns a Set<String>
         * get available zoneIDs using getAvailableIds() on TimeZOne class - returns a String[]
         * Set a new HashSet of alternate using Set.of(String[] alternate)
         *
         * Use setMath to see find out the diff between these 2
         *
         */

        Set<String> jdk8Zones = ZoneId.getAvailableZoneIds();
        String[] alternate = TimeZone.getAvailableIDs();
        Set<String> oldWay = new HashSet<>(Set.of(alternate));

        /*
         * Remove all zoneIDs from jdk8Zones
         * returns an []
         * This means ZoneId class returns all the same zones that TimeZOne class has
         */

        //jdk8Zones.removeAll(oldWay);
        //System.out.println(jdk8Zones);

        /*
         * Is this true for the other way round
         * Let's find out
         * We get below result
         * [JST, IST, BET, ACT, HST, AET, AGT, VST, CNT, EST, NET, PLT, CST, SST, CTT, PNT, BST,
         *  MIT, ART, AST, PRT, PST, IET, NST, EAT, MST, ECT, CAT]
         * This means TimeZOne class has time zones which the newer ZoneId doesn't
         * These are really daylight savings codes but were once valid Zone Ids
         * Their use is being discouraged though not officially deprecated
         */
        oldWay.removeAll(jdk8Zones);
        System.out.println(oldWay);

        /*
         * Can we create a ZoneId instance using one of the above TimeZone codes
         * Let's try that next with BET
         * This throws ZoneRulesException for Unknown time-zone ID BET
         * There is an overloaded version that takes a 2nd arg which is a
         * Map of Ids that we get from a static field on ZoneId
         *
         * Prints America/Sao_Paulo in Brazil in South America or just America
         */
        // bet = ZoneId.of("BET"); // throws ZoneRulesException error
        ZoneId bet = ZoneId.of("BET",ZoneId.SHORT_IDS);
        System.out.println(bet);

        /*
         * Set our clock to the same time and place
         * Done using System class - setProperty() that let's us set system variables
         * However, needs to be done before any date manipulation code b4 it's cached,
         * otherwise it won't have any effect on the JVM timezone
         * Let's add it as the first statement in our main() here
         * Let's use America/Los_Angeles
         *
         * Print America/Los_Angeles instead of Africa/Nairobi
         *
         * Let's print the time now in LA
         * Prints - 2024-03-18T01:44:47.595202300
         */
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        /*
         * Instance Class
         * You can create an instance of the Instance class using the now() just like we did with LocalDateTime
         * Prints - 2024-03-18T08:48:37.991166200Z
         * We don't get the same datetime as above
         * It has the same default format as a local date time with the date followed by T then time including nanoseconds
         * We also have a trailing Z character - indicates time is UTC or a zero offset
         */
        Instant instantNow = Instant.now();
        System.out.println(instantNow);



    }
}
