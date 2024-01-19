package Fundamentals.coding_challenges;

public class SecondsAndMinutesChallenge {

    public static void main(String[] args) {
        System.out.println(getDurationString(3945));
        System.out.println(getDurationString(65,45));
    }

    /*
    * 1sec = 1000ms
    * 1min = 60 seconds
    * 1hr = 6o min or (3600sec)
    * */

    public static String getDurationString(int seconds){

        int minutes = seconds / 60;                 // (3945 / 60) = 65.75 min = 1hr 5min 45s(0.75*60)
        return getDurationString(minutes,seconds);  // getDurationString(65,3945)
    }
    public static String getDurationString(int minutes ,int seconds) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        int remainingSeconds = seconds % 60;
        return hours+"h " + remainingMinutes+"m " +remainingSeconds+"s";
    }
}
