package Fundamentals.control_flow.coding_exercises;

public class FlourPacker {

    public static void main(String[] args) {
        System.out.println(canPack(1, 0, 5));
    }

    public static boolean canPack(int bigCount, int smallCount, int goal){
        if(!isNegative(bigCount) || !isNegative(smallCount) || !isNegative(goal))
            return false;

       // bigCount rep count of big flour bags 5kg each
       // smallCount rep count of small flour bags 1kg each
       // goal rep amount of kilos of flour needed to assemble a package

        // (bigCount + smallCount) <= goal

        // (bigCount + smallCount) >= goal -
        int totalKilos = (5 * bigCount) + smallCount;

        if( totalKilos > goal){
            return false;
        } else if (totalKilos == goal) {
            return true;
        }else{

        }
        return false;

    }

    public static boolean isNegative(int number){
        return number > -1;
    }
}
