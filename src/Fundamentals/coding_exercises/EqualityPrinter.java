package Fundamentals.coding_exercises;

public class EqualityPrinter {

    private static final String INVALID_VALUE_MESSAGE = "Invalid Value";
    private static final String ALL_EQUAL_MESSAGE = "All numbers are equal";
    private static final String NEITHER_ALL_EQUAL_MESSAGE = "Neither all are equal or different";
    private static final String ALL_DIFF_MESSAGE = "All numbers are different";


    public static void main(String[] args) {
        printEqual(1,1,1);
        printEqual(1,1,2);
        printEqual(1,2,1);
        printEqual(2,1,1);
        printEqual(1,2,3);
        printEqual(2,21,21);
        printEqual(0,1,2);
        printEqual(-1,1,1);
        printEqual(1,-1,1);
        printEqual(1,1,-1);
        printEqual(-1,-1,1);
        printEqual(1,-1,-1);
        printEqual(-1,-1,-1);
    }

    public static void printEqual(int x,int y, int z){

        if(x < 0 || y < 0 || z < 0){
            System.out.println(INVALID_VALUE_MESSAGE);
        }else if (x == y && y == z){
            System.out.println(ALL_EQUAL_MESSAGE);
        }else if(x == y || x ==z || y == z){
            System.out.println(NEITHER_ALL_EQUAL_MESSAGE);
        }else{
            System.out.println(ALL_DIFF_MESSAGE);
        }
    }
}
