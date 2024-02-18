package arrays.array_search_methods;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String[] sArray = {"Able","Jane","Mark","Ralph","David"};
        Arrays.sort(sArray);

        System.out.println(Arrays.toString(sArray));

        //  Arrays.binarySearch(array , search)  ////
        //Array MUST be sorted
        // Returns  position of the element , Otherwise -1
        if(Arrays.binarySearch(sArray,"Mark") >= 0){
            System.out.println("Found Mark in the list");
        }

        // Equality Testing
        //Arrays.equals(array)
        // Compare 2 Arrays whether are equal
        //Returns boolean
        int[] s1 = {1,2,3,4,5};
        int[] s2 = {5,2,3,4,1};
        if (Arrays.equals(s1,s2))
            System.out.println("Arrays are equal");
        else
            System.out.println("Arrays are not equal");
    }
}
