package Fundamentals.coding_exercises;

public class MinutesToYearsDaysCalculator {
    private static final int MIN_PER_HOUR = 60;
    private static final int HOURS_PER_DAY = 24;
    private static final int DAYS_PER_YEAR = 365;
    private static final int MIN_PER_DAY = MIN_PER_HOUR * HOURS_PER_DAY;
    private static final int MIN_PER_YEAR = MIN_PER_DAY * DAYS_PER_YEAR;

    public static void main(String[] args) {
        printYearsAndDays(-525600);
        printYearsAndDays(-1440);
        printYearsAndDays(-60);
        printYearsAndDays(0);
        printYearsAndDays(-1440);
        printYearsAndDays(525600);
        printYearsAndDays(1051200);
        printYearsAndDays(581760);
        printYearsAndDays(527040);
        printYearsAndDays(561600);
        printYearsAndDays(1788480);
        printYearsAndDays(3152160);
    }


    public static void printYearsAndDays(long minutes){

        if (minutes < 0) {
            System.out.println("Invalid Value");
            return;
        }
        long years = minutes / MIN_PER_YEAR;
        long days = (minutes / MIN_PER_DAY) % 365;


        System.out.println(minutes+ " min = "+years+" y and "+days+" d");

    }

    //3152160 min
    //hours..? =  (3152160 / 60 ) = 52,536hrs
    //days..? = (52536 / 24) = 2189days
    //years.? = (2189 / 365 ) = 5 yrs and
   // remaining days (2189 % 365) = 364 days

}
