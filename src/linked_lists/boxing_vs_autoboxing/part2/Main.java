package linked_lists.boxing_vs_autoboxing.part2;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        //Calling Below methods
        Double resultBoxed = getLiteralDoublePrimitive();
        double resultUnBoxed = getDoubleObject();

        //Array of Integer wrappers - sized 5
        Integer[] wrapperArray = new Integer[5];
        wrapperArray[0] = 50;
        System.out.println(Arrays.toString(wrapperArray));
        System.out.println(wrapperArray[0].getClass().getName());

        //Autoboxing with Array Initializer
        Character[] charArray = {'a','b','c','d'};
        System.out.println(Arrays.toString(charArray));

        //Call getList()
        //  var ourList = List.of(1,2,3,4,5);
        var ourList = getList(1,2,3,4,5);
        System.out.println(ourList);
    }

    private static ArrayList<Integer> getList(Integer... varags){
        ArrayList<Integer> aList = new ArrayList<>();
        for (int i: varags) {
            aList.add(i);
        }
        return aList;
    }

    private static int returnAnInt(Integer i){
        return i;
    }

    private static Integer returnAnInteger(int i){
        return i;
    }
    private static Double getDoubleObject(){
        return Double.valueOf(100.00);
    }

    private static double getLiteralDoublePrimitive(){
        return 100.0;
    }
}
