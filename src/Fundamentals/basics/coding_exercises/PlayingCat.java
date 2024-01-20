package Fundamentals.basics.coding_exercises;

public class PlayingCat {

    public static void main(String[] args) {
        System.out.println(isCatPlaying(true,10));
        System.out.println(isCatPlaying(true,33));
        System.out.println(isCatPlaying(false,25));
        System.out.println(isCatPlaying(true,25));
        System.out.println(isCatPlaying(true,45));
        System.out.println(isCatPlaying(true,46));
        System.out.println(isCatPlaying(false,45));
        System.out.println(isCatPlaying(false,46));
        System.out.println(isCatPlaying(false,50));
        System.out.println(isCatPlaying(false,36));
        System.out.println(isCatPlaying(true,36));
        System.out.println(isCatPlaying(false,35));
        System.out.println(isCatPlaying(true,35));
        System.out.println(isCatPlaying(true,24));
    }

    private static boolean isCatPlaying(boolean summer, int temperature){


       /* if(summer && (temperature > 24 && temperature <  46))
            return true;

        if(!summer && (temperature > 24 && temperature <  36))
            return true;

        return false;
        */

        int max = summer ? 46 : 36;
        return temperature > 24 && temperature <  max;
    }
}
