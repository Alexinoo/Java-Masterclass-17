package nuts_and_bolts.part10_instant_duration_period_zdt;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class MAin {
    public static void main(String[] args) {

        System.setProperty("user.timezone","America/Los_Angeles");

        Instant instantNow = Instant.now();

        /*
         * Use an instance with a time zone to examine daylight savings rule
         * First - Create a  ZoneId[] of 3 ZoneIds
         * Loop and set up a DateTimeFormatter using .ofPattern()
         * Then pass a String literal containing "z" and a set of 4 z's which outputs the time zone for a date
         * Print each zone id in a separate line followed by a tabbed info about instantNow.atZone() formatted with specified format
         * Print each zone id rules and daylight savings
         *
         * Let's look at Africa/Nairobi
         * Prints zone id as Africa/Nairobi - no surprises there
         * following are daylight savings rules - EAT:East Africa Time
         * Then prints (PT0S) a format of duration - starts with PT which stands for Period Of Time (PT) followed by a numeric value (0)
         * and the time unit used for the duration (S - seconds)
         * false - Will only get offset if the flag is daylight savings is set to true
         *
         */
        ZoneId[] zoneIDs = {
                    ZoneId.of("Australia/Sydney"),
                    ZoneId.of("Europe/Paris"),
                    ZoneId.of("America/New_York"),
                    ZoneId.of("Africa/Nairobi")
        };

        for(ZoneId z : List.of(zoneIDs)){
            DateTimeFormatter zoneFormat = DateTimeFormatter.ofPattern("z:zzzz");
            System.out.println(z);
            System.out.println("\t" + instantNow.atZone(z).format(zoneFormat));
            System.out.println("\t" + z.getRules().getDaylightSavings(instantNow));
            System.out.println("\t" + z.getRules().isDaylightSavings(instantNow));
        }

        /*
         * We can create an instance by passing a string (Let's say a dob for example)
         * For the Instant class, there's only one version of parse, therefore, your String must be in the default output format
         * This has to be a valid instant in UTC
         * Parsed using DateTimeFormatter.ISO_INSTANT
         * You can turn an instance into a local date time using the LocalDateTime.ofInstant()
         * We can pass our dobInstant variable and the system default time zone which we have changed to be LA
         * Print this using the Medium form of localized date and time
         *
         * Your kid's birthdate, LA time = 1 Jan 2020, 00:01:00
         */
        Instant dobInstant = Instant.parse("2020-01-01T08:01:00Z");
        LocalDateTime dob = LocalDateTime.ofInstant(dobInstant,ZoneId.systemDefault());
        System.out.println("Your kid's birthdate, LA time = "+
                dob.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        /*
         * ZonedDateTime() - Represents a date-time along with the time zone
         * It's fields are LocalDateTime and ZoneId
         */

        ZonedDateTime dobSydney = ZonedDateTime.ofInstant(dobInstant,ZoneId.of("Australia/Sydney"));
        System.out.println("Your kid's birthdate, Sydney time = "+
                dobSydney.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        /*
         * Use ZonedDateTime to get another ZonedDateTime in a different time zone
         */
        ZonedDateTime dobHere = dobSydney.withZoneSameInstant(ZoneId.systemDefault());
        System.out.println("Your kid's birthdate, Here time = "+
                dobHere.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        /*
         * Suppose we want to know when the first day of the next month is, starting with today
         * There is a pre-defined TemporalAdjuster to achieve just that
         * use with(TemporalAdjusters.firstDayOfNextMonth())
         * Ctrl click on TemporalAdjusters
         * Select View > Tool Windows > Structure
         */
        ZonedDateTime firstOfMonth = ZonedDateTime.now()
                .with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.printf("First of next month = %tD %n", firstOfMonth);

        /*
         * THere is another class similar to ZonedDateTime called OffsetDate that just has the UTC offset and the
         * LocalDateTime
         * There's also an OffsetTime which is LocalTime joined with an offset
         * Neither of these classes uses daylight savings rules
         */

        /*
         * Let's look at Temporal amounts which is what you get when you use Durations and Periods
         * Periods are date-based related to years,months,days
         * Create a Period instance  using a static () on Period class .between()  that takes 2 LocalDate types
         * i.e. startDate inclusive and endDate exclusive
         *
         * Prints P54Y2M17D Rep Period- 54Years-2Months-17Days
         */
        //System.out.println(LocalDate.EPOCH); //1970-01-01
        Period timePast = Period.between(LocalDate.EPOCH ,LocalDate.now() );
        System.out.println(timePast);

        /*
         * Create something similar with Durations
         * Prints PT438288H1M PT - Period of Time, 438288H-hours , 1m-minute
         */
        Duration timeSince = Duration.between(Instant.EPOCH, dob.toInstant(ZoneOffset.UTC));
        System.out.println(timeSince);

        //
        LocalDateTime dob2 = dob.plusYears(2).plusMonths(4).plusDays(4)
                            .plusHours(7).plusMinutes(14).plusSeconds(37);

        System.out.println("Your 2nd kid's birthdate, Here time = "+
                dob2.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        /*
         * between() on ChronoUnit enum
         */
        for (ChronoUnit u : ChronoUnit.values()){
            if(u.isSupportedBy(LocalDate.EPOCH)){
                long val = u.between(LocalDate.EPOCH, dob2.toLocalDate());
                System.out.println(u + " past = "+val);
            }
            System.out.println("--Not supported :" +u);
        }
















    }
}
