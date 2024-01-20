package Fundamentals.basics.coding_challenges;

public class SecAndMinBonusChallenge {
    public static void main(String[] args) {
        System.out.println(getDurationString(-3945));
        System.out.println(getDurationString(-65,45));
        System.out.println(getDurationString(65,145));

        System.out.println(getDurationString(3945));
        System.out.println(getDurationString(65,45));

        System.out.println(getDurationString(4000));
    }

    public static String getDurationString(int seconds){

        if(seconds < 0)
            return "Invalid data for seconds(" +seconds+"), must be a positive integer value";

        int minutes = seconds / 60;                 // (3945 / 60) = 65.75 min = 1hr 5min 45s(0.75*60)
        int remainingSeconds = seconds % 60;                 // (3945 % 60) = 45 remaining sec after hrs & min were extracted
        return getDurationString(minutes,remainingSeconds);  // getDurationString(65,3945)
    }
    public static String getDurationString(int minutes ,int seconds) {
        if(minutes < 0)
            return "Invalid data for minutes(" +minutes+"), must be a positive integer value";

        if(seconds <= 0 || seconds >= 59)
            return "Invalid data for seconds(" +seconds+"), must be between 0 and 59";

        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;

        return hours+"h " + remainingMinutes+"m " +seconds+"s";
    }
}
