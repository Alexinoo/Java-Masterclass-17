package arrays.part_2;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        //Array Initialized with 0 values
        int[] newArray;
        newArray = new int[5];

        //Printing out default values 0 0 0 0 0
        for (int i=0; i< newArray.length;i++){
            System.out.print(newArray[i]+" ");
        }

        System.out.println();

        //Array Initialized with 5 values
        int[] anotherArray;
        anotherArray = new int[5];

        // Initialize with 5 4 3 2 1
        for (int i = 0; i < anotherArray.length; i++) {
            anotherArray[i] = anotherArray.length - i;
        }

        //Printing out 5 4 3 2 1
        for (int i=0; i< anotherArray.length;i++){
            System.out.print(anotherArray[i]+" ");
        }

        System.out.println();

        //Enhanced for loop
        for (int element: anotherArray) {
            System.out.print(element+" ");
        }

        System.out.println();

        //Print an Array from the get go without looping
        System.out.println(Arrays.toString(anotherArray));


        System.out.println();

        //Assign an Array to an object variable
        Object objVariable = anotherArray;
        if(objVariable instanceof int[]){
            System.out.println("objVariable is really an int array");
        }


        //Create an Array of Objects that support any primitive kind in Java
        Object[] objArray = new Object[3];
        objArray[0] = "Hello";               // String literal
        objArray[1] = new StringBuilder("World"); // StringBuilder instance
        objArray[2] = anotherArray; // Array of integers


    }
}
